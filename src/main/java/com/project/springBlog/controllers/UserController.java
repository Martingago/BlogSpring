package com.project.springBlog.controllers;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.mapper.UserMapper;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Set;

@Controller
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

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
