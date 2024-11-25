package com.project.springBlog.controllers;

import com.project.springBlog.config.CustomUserDetails;
import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.dtos.user.UserResponseDTO;
import com.project.springBlog.mapper.UserMapper;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.services.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;


    @PostMapping("/public/login")
    public ResponseEntity<ResponseDTO> loginUser(@Valid @RequestBody UserDTO userDTO){
        try {
            // Validación básica de entrada
            if (userDTO.getUsername() == null || userDTO.getPassword() == null) {
                return new ResponseEntity<>(new ResponseDTO(false, "Username and password must be provided", null), HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );
            //Establece el login de usuario
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Obtener detalles del usuario authenticado
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            UserModel usuario = userService.findUserById(customUserDetails.getId());
            UserDTO loginDTO = UserMapper.toDTO(usuario);

            //Respuesta del login
            return new ResponseEntity<>(new ResponseDTO(true, "Login successfully", loginDTO ), HttpStatus.OK);
        }catch (AuthenticationException ex){
            return new ResponseEntity<>(new ResponseDTO(false, "Username and password are invalid", false), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Obtiene los datos de un usuario tanto a través de su ID, como de su username
     * @return map de userDTO con la informacion de un usuario
     */
    @GetMapping("public/profile/{id}")
    public ResponseEntity<ResponseDTO> getUser(@PathVariable("id") String id){
        UserModel userModel = userService.findUserByIdOrUsername(id);

        if(userModel != null){
            UserDTO userDTO = UserMapper.toDTO(userModel);
            return new ResponseEntity<>(new ResponseDTO(true, "User succesfully founded", userDTO), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseDTO(false, "User not found", null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("admin/users")
    public ResponseEntity<ResponseDTO> getUsersPaginated(
            @RequestParam(required = false, defaultValue = "id") String field,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "12") int size
    ){
        Page<UserResponseDTO> usersDTO = userService.getUsersPaginated(field,order,page,size);
        return new ResponseEntity<>(new ResponseDTO(true, "List of Users", usersDTO), HttpStatus.OK);
    }
    /**
     * Encuentra y devuelve un listado de comentarios escritos por un usuario especificado
     * Para poder acceder al listado de comentarios de un usuario se debe estar authenticado como el propio
     * usuario o como un usuario con rol de admin
     * @param id identificador del usuario sobre el que buscar los comentarios - id o username
     * @return ResponseDTO que incluye estado de la operacion y listado de comentarios en caso de existir
     */
    @GetMapping("user/profile/{id}/comments")
    public ResponseEntity<ResponseDTO> getUserComments(@PathVariable("id") String id) {
        String username_req = SecurityContextHolder.getContext().getAuthentication().getName(); //Datos de quien hace la consulta
        UserModel usuario = userService.findUserByIdOrUsername(id); //Busca al usuario en la base de datos

        if(!usuario.getUsername().equals(username_req)){
            //Si el usuario que realiza la consulta no coincide con el usuario a buscar se comprueba que sea admin
            UserModel userAdmin = userService.findByUsername(username_req);
            if(!userAdmin.getRoles().contains("ADMIN")){
                throw new SecurityException("No permission to get that user data");
            }
        }

        Set<CommentDTO> setComments = userService.getUserComments(usuario);
        return new ResponseEntity<>(new ResponseDTO(true, "List of user comments found succesfully", setComments), HttpStatus.OK);
    }


}
