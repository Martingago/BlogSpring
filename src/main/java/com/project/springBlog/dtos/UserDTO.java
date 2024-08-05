package com.project.springBlog.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserDTO {

    private String username;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> roles; //Id de los roles de un usuario

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<String> userRoles; //Strings de los roles de usuario

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Long> userPublicaciones;

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
    public UserDTO(String username, String password, List<Long> roles, String name) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.name = name;
    }

    /**
     * Constructor de usuario información detallada para enviar al front-end
     * @param username
     * @param roles
     * @param name
     * @param userPublicaciones
     */
    public UserDTO(String username, Set<String> roles, String name, Set<Long> userPublicaciones){
        this.username = username;
        this.userRoles = roles;
        this.name = name;
        this.userPublicaciones = userPublicaciones;
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

    public Set<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<String> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<Long> getUserPublicaciones() {
        return userPublicaciones;
    }

    public void setUserPublicaciones(Set<Long> userPublicaciones) {
        this.userPublicaciones = userPublicaciones;
    }
}
