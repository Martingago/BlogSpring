package com.project.springBlog.controllers;

import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Crea un usuario en la base de datos
     * @param usuario
     * @return
     */
    @PostMapping("/register/user")
    public UserModel registerUser(@RequestBody UserModel usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); //Se codifica el string de password
        return userRepository.save(usuario);
    }
}
