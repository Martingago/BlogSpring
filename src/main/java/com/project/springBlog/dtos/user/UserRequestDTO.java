package com.project.springBlog.dtos.user;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

/**
 * DTO que se solicita a la hora de crear un usuario básico en la base de datos
 */
public class UserRequestDTO {

    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    //Atributo opcional que se puede recibir a la hora de generar un usuario con roles administrativos
    private List<Long> roles;

    public UserRequestDTO() {
    }

    //User normal
    public UserRequestDTO(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    //User roles administrativos
    public UserRequestDTO(String username, String password, String name, List<Long> roles) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }
}
