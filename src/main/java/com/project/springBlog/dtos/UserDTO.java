package com.project.springBlog.dtos;

import com.project.springBlog.models.UserModel;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    private List<Long> roles;

    @NotEmpty(message = "Name cannot be empty")
    private String name;


    /**
     * Constructor sin argumentos
     */
    public UserDTO(){}

    /**
     * Constructor para un usuario básico sin roles.
     * @param username
     * @param password
     * @param name
     */

    public UserDTO(String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
        this.roles = new ArrayList<>();
    }

    /**
     * Contructor para un usuario con roles de administracion
     * @param username
     * @param password
     * @param roles
     * @param name
     */
    public UserDTO(String username, String password, List<Long> roles, String name){
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.name = name;
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public  String getName() {
        return name;
    }

    public void setName( String name) {
        this.name = name;
    }
}
