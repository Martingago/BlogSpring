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

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    /**
     * Crea un usuario sin privilegios en la base de datos
     * @param usuarioDTO
     * @return
     */
    @PostMapping("/register/user")
    public ResponseEntity<ResponseDTO> registerUser(@Valid @RequestBody UserDTO usuarioDTO, BindingResult result){
        if(result.hasErrors()){
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        try {
            UserModel newUser = userService.createUser(usuarioDTO); //Crea un usuario con permisos básicos
            UserDTO userDTO = UserMapper.toSimpleDTO(newUser);
            return new ResponseEntity<>(new ResponseDTO(true, "User was successfully created", userDTO), HttpStatus.OK);
        }catch (DuplicateKeyException ex){
            return new ResponseEntity<>(new ResponseDTO(false, "The username already exists", null), HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false, "An unexpected error occurred", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Crea un usuario administrador en la base de datos
     * Los roles de un ADMIN pueden ser: USUARIO, ADMIN, EDITOR
     * @param usuarioDTO
     * @return
     */
    @PostMapping("/register/admin")
    @Transactional
    public ResponseEntity<ResponseDTO> registerAdmin(@RequestBody UserDTO usuarioDTO, BindingResult result){
        if(result.hasErrors()){
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        try{
            UserModel newUser = userService.createUser(usuarioDTO, true); //Crea un usuario normal
            UserDTO userDTO = UserMapper.toSimpleDTO(newUser);
            return new ResponseEntity<>(new ResponseDTO(true, "User was successfully created", userDTO), HttpStatus.OK);
        }catch (DuplicateKeyException ex){
            return new ResponseEntity<>(new ResponseDTO(false, "The username already exists", null), HttpStatus.CONFLICT);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ResponseDTO(false, "An unexpected error occurred", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/admin/user/{id}")
    public boolean deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
