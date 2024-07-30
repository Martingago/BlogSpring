package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.project.springBlog.dtos.RoleDTO;
import jakarta.persistence.*;

import javax.management.relation.Role;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name="usuarios")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="username", unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleModel> rolesList = new HashSet<>();

    @Column(nullable = false)
    private String name;

    //Listado de post creados por un usuario
    @OneToMany(mappedBy = "creador", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<PostDetailsModel> postList = new HashSet<>();

    /**
     * Añade un rol a un usuario
     * @param rol
     */
    public void addRol(RoleModel rol){
        this.rolesList.add(rol);
        rol.getUsers().add(this);
    }

    /**
     * Elimina un rol a un usuario
     * @param rol
     */
    public void deleteRol(RoleModel rol){
        this.rolesList.remove(rol);
        rol.getUsers().remove(this);
    }

    /**
     * Funcion que establece que datos se van a enviar al front de la información de los roles
     * @return Set de objeto RoleDTO
     */
    @JsonGetter("rolesList")
    public Set<RoleDTO> serializedRolesList(){
        Set<RoleDTO> rolesDTOList = new HashSet<>();
        for(RoleModel rol : rolesList){
            rolesDTOList.add(new RoleDTO(rol.getId(), rol.getRoleName()));
        }
        return rolesDTOList;
    }

    // Constructores

    public UserModel(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public UserModel() {
    }

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Set<RoleModel> getRolesList() {return rolesList;}

    public void setRolesList(Set<RoleModel> rolesList) {this.rolesList = rolesList;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PostDetailsModel> getPostList() {return postList;}

    public void setPostList(Set<PostDetailsModel> postList) {this.postList = postList;}

}
