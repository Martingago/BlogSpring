package com.project.springBlog.services;

import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Funcion que obtiene los datos de un usuario pasando como parametro su nombre
     * @param username
     * @return
     */
    public UserModel findByUsername(String username){
        Optional<UserModel> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return user.get();
        }else{
            throw  new UsernameNotFoundException("Username was not founded");
        }
    }

    /**
     * Funcion que emplea la sobrecarga del método createUser(UserModel, boolean) para crear un usuario de roll USER
     * Inyecta y sobreescribe automácticamente el roll de USER al objeto recibido para evitar vulnerabilidades de roles.
     * @param newUser datos del usuario a crear
     * @return objeto creado en la BBDD con el roll fijado en USER
     */
    public UserModel createUser(UserModel newUser){
        return createUser(newUser, false);
    }

    /**
     * Funcion que permite crear un usuario con roll de USER, o roles de ADMINISTRACION
     * Los roles deben ser recibidos en el objeto newUser, no se produce ninguna inyección por defecto
     * @param newUser informacion del usuario a crear
     * @param isAdmin boolean indicando si es ADMINISTRADOR o no
     * @return objeto creado en la BBDD con el roll recibido
     */
    public UserModel createUser(UserModel newUser, boolean isAdmin){
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword())); //Se codifica el string de password
        if(!isAdmin){
            newUser.setRole("USER"); //Asegura que el rol sea solo USER y que no se pueda inyectar desde el front
        }
        return userRepository.save(newUser);
    }

    /**
     * Elimina los datos de un usuario con un ID especificado.
     * ESTA FUNCION NO COMPRUEBA PERMISOS DE USUARIO. Es necesario comprobar previamente el AUTH de quien está eliminando datos
     * @param id del usuario a eliminar
     * @return boolean si ha tenido éxito, error si el usuario no se ha encontrado.
     */
    public boolean deleteUser(Long id){
        Optional<UserModel> userDelete =  userRepository.findById(id);
        if(userDelete.isPresent()){
            userRepository.deleteById(userDelete.get().getId());
            return true;
        }else{
            throw  new UsernameNotFoundException("User with id " + id + " was not founded");
        }
    }

    /**
     * Realiza una authenticacion del usuario que realiza una solicitud y devuelve los datos de dicho usuario
     * @return
     */
    public UserModel getUserAuth(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return findByUsername(auth.getName());
    }
}
