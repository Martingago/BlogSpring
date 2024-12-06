package com.project.springBlog.dtos.user;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class UserResponseDTO {

    private Long id;
    @NotEmpty(message = "Username cannot be empty")
    private String username;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotEmpty(message = "User must have at least one role")
    private Set<String> roles;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String username, String name, Set<String> roles) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
