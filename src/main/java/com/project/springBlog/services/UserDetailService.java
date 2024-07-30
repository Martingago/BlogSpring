package com.project.springBlog.services;

import com.project.springBlog.models.RoleModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailService implements UserDetailsService {
    //Informacion adicional: https://www.youtube.com/watch?v=9J-b6OlPy24

    @Autowired
    private UserRepository userRepository;

    /**
     * Carga la informacion de un usuario a través de su username
     * @param username
     * @return objeto userDetails con la información correspondiente a ese usuario
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            var userObj = user.get();
            userObj.getRolesList().size(); //Fuerza la obtencion de los roles de usuario
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getUserRoles(userObj))
                    .build();
        }else{
            throw new UsernameNotFoundException("Username was not founded");
        }
    }

    /**
     * Obtiene un array con los roles que tiene un usuario especificado
     * @param user
     * @return
     */
    private String[] getUserRoles(UserModel user){
        //Conjunto de roles que se obtienen del usuario
        Set<RoleModel> roles = user.getRolesList();
        //Se convierten los RoleModels a un array de Strings con los nombres de ROL
        String[] roleName = roles.stream()
                .map(rol -> rol.getRoleName())
                .toArray(String[]::new);
        return roleName;
    }


}
