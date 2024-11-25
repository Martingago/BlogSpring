package com.project.springBlog.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name="usuarios")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username", unique = true, nullable = false)
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @Column(nullable = false)
    @NotEmpty(message = "password cannot be empty")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleModel> rolesList = new HashSet<>();

    //Comentarios escritos por un usuario
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentModel> comentariosList = new HashSet<>();

    @Column(nullable = false)
    @NotEmpty(message = "Name cannot be empty")
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

    public void addPost(PostDetailsModel details){
        this.postList.add(details);
        details.setCreador(this);
    }

    public void deletePost(PostDetailsModel details){
        this.postList.remove(details);
        details.setCreador(null);
    }

    /**
     * Obtiene un set de los roles de un usuario
     * @return set de Strings con los roles que tiene un usuario
     */
    public Set<String> getRoles(){
        return this.getRolesList().stream()
                .map(RoleModel::getRoleName)
                .collect(Collectors.toSet());
    }

    /**
     * Obtiene un set de las publicaciones de un usuario
     * @return set con Id de las publicaciones que ha creado un usuario
     */
    public Set<Long> getPublicaciones(){
        return this.getPostList().stream()
                .map(PostDetailsModel::getId)
                .collect(Collectors.toSet());
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

    public Set<CommentModel> getComentariosList() {
        return comentariosList;
    }

    public void setComentariosList(Set<CommentModel> comentariosList) {
        this.comentariosList = comentariosList;
    }
}
