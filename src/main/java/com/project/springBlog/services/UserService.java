package com.project.springBlog.services;

import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.RoleModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import com.project.springBlog.repositories.RoleRepository;
import com.project.springBlog.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    private PostDetailsRepository detailsRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

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
    public UserModel createUser(UserDTO newUser){
        return createUser(newUser, false);
    }

    /**
     * Funcion que permite crear un usuario con roll de USER, o roles de ADMINISTRACION
     * Los roles deben ser recibidos en el objeto newUser, no se produce ninguna inyección por defecto
     * @param newUserDTO informacion del usuario a crear
     * @param isAdmin boolean indicando si es ADMINISTRADOR o no
     * @return objeto creado en la BBDD con el roll recibido
     */
    public UserModel createUser(UserDTO newUserDTO, boolean isAdmin){
        //Comprueba que el usuario no exista en la BBDD
        Optional<UserModel> existingUser = userRepository.findByUsername(newUserDTO.getUsername());
        if(existingUser.isPresent()){
            throw new DuplicateKeyException("The username is already exists");
        }

        newUserDTO.setPassword(passwordEncoder.encode(newUserDTO.getPassword())); //Se codifica el string de password
        //Se crea un modelo de usuario
        UserModel newUser = new UserModel(newUserDTO.getUsername(), newUserDTO.getPassword(), newUserDTO.getName());
        if(!isAdmin){
            RoleModel userRol = roleRepository.findByRoleName("USER").get();
            newUser.addRol(userRol);
        }else{
            roleService.insertRolesToUser(newUser, newUserDTO.getRoles()); //Le añade los roles correspondientes
        }
        userRepository.save(newUser);
        return newUser;
    }

    /**
     * Elimina los datos de un usuario con un ID especificado.
     * ESTA FUNCION NO COMPRUEBA PERMISOS DE USUARIO. Es necesario comprobar previamente el AUTH de quien está eliminando datos
     * @param id del usuario a eliminar
     * @return boolean si ha tenido éxito, error si el usuario no se ha encontrado.
     */
    @Transactional
    public boolean deleteUser(Long id){
        Optional<UserModel> userDelete =  userRepository.findById(id);
        if(userDelete.isPresent()){
            UserModel userToDelete = userDelete.get();
            if(!userToDelete.getPostList().isEmpty()){
                //Se modifican los post asociados a dicho usuario y se establece su creador en la administracion
                UserModel userAdmin = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("Admin user not found"));

                for(PostDetailsModel details : userToDelete.getPostList()){
                    details.setCreador(userAdmin);
                    detailsRepository.save(details);
                }
            }
            userRepository.delete(userToDelete);
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
