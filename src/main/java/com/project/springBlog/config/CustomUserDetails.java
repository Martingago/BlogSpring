package com.project.springBlog.config;


import com.project.springBlog.models.UserModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final UserModel user;

    public CustomUserDetails(UserModel user){
        this.user = user;
    }

    /**
     * Convierte los roles de un usuario establecidos en la base de datos en un GrantedAuthority
     * @return colección con los roles de un usuario convertidos en un SimpleGrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRolesList().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" +role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //Métodos para obtener atributos necesarios del usuario

    public Long getId() {
        return user.getId();
    }

}
