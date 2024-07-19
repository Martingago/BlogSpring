package com.project.springBlog.services;

import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
//https://www.youtube.com/watch?v=9J-b6OlPy24
    @Autowired
    private UserRepository userRepository;

    /**
     * Carga la informacion de un usuario a través de su username
     * @param username
     * @return objeto userDetails con la información correspondiente a ese usuario
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            var userObj = user.get();
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
        if(user.getRole() == null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}
