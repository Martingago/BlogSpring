package com.project.springBlog.services;

import com.project.springBlog.config.CustomUserDetails;
import com.project.springBlog.models.RoleModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        UserModel user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Username was not founded on database"));
        user.getRolesList().size();
        return new CustomUserDetails(user);
    }


}
