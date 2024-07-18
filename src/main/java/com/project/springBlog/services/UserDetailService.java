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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUserName(username);
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

    private String[] getUserRoles(UserModel user){
        if(user.getRole() == null){
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}
