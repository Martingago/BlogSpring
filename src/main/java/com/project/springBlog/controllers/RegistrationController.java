package com.project.springBlog.controllers;

import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.services.UserService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    @Autowired
    UserService userService;

    /**
     * Crea un usuario sin privilegios en la base de datos
     * @param usuario
     * @return
     */
    @PostMapping("/register/user")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserModel usuario, BindingResult result){
        if(result.hasErrors()){
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        UserModel newUser = userService.createUser(usuario);
        return new ResponseEntity<>(new ResponseDTO(true, "User was successfully created", newUser), HttpStatus.OK);
    }

    /**
     * Crea un usuario administrador en la base de datos
     * Los roles de un ADMIN pueden ser: USUARIO, ADMIN, EDITOR
     * @param usuario
     * @return
     */
    @PostMapping("/register/admin")
    public ResponseEntity<ResponseDTO> registerAdmin(@RequestBody UserModel usuario, BindingResult result){
        if(result.hasErrors()){
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        UserModel newUser = userService.createUser(usuario, true);
        return new ResponseEntity<>(new ResponseDTO(true, "User was successfully created", newUser), HttpStatus.OK);
    }

    @DeleteMapping("/admin/user/{id}")
    public boolean deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
