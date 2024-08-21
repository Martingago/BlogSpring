package com.project.springBlog.controllers;

import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.mapper.UserMapper;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.services.RoleService;
import com.project.springBlog.services.UserService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    @Autowired
    UserService userService;

    /**
     * Crea un usuario sin privilegios en la base de datos
     * @param usuarioDTO
     * @return
     */
    @PostMapping("/register/user")
    @Transactional
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDTO usuarioDTO, BindingResult result){
        if(result.hasErrors()){
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        try {
            UserModel newUser = userService.createUser(usuarioDTO); //Crea un usuario con permisos básicos
            UserDTO userDTO = UserMapper.toDTO(newUser);
            return new ResponseEntity<>(new ResponseDTO(true, "User was successfully created", userDTO), HttpStatus.OK);
        }catch (DuplicateKeyException ex){
            return new ResponseEntity<>(new ResponseDTO(false, "The username already exists", null), HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Crea un usuario administrador en la base de datos
     * Los roles de un ADMIN pueden ser: USUARIO, ADMIN, EDITOR
     * @param usuarioDTO DTO  recibido desde el front-end del usuario que se quiere registrar
     * @param result
     * @return ResponseEntity con un booleano de exito, mensaje y userDTO
     */
    @PostMapping("/register/admin")
    @Transactional
    public ResponseEntity<ResponseDTO> registerAdmin(@Valid @RequestBody UserDTO usuarioDTO, BindingResult result){
        if(result.hasErrors()){
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        try{
            UserModel newUser = userService.createUser(usuarioDTO, true); //Crea un usuario normal
            UserDTO userDTO = UserMapper.toDTO(newUser);
            return new ResponseEntity<>(new ResponseDTO(true, "User was successfully created", userDTO), HttpStatus.OK);
        }catch (DuplicateKeyException ex){
            return new ResponseEntity<>(new ResponseDTO(false, "The username already exists", null), HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false, e.getMessage(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Elimina un usuario de la Base de datos
     * @param id del usuario a eliminar
     * @return
     */
    @DeleteMapping("/admin/user/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(new ResponseDTO(true, "User successfully deleted", null), HttpStatus.OK);

    }
}
