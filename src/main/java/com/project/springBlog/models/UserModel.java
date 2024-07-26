package com.project.springBlog.models;

import jakarta.persistence.*;

import java.util.HashSet;
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

    @Column(nullable = false)
    private String role; //ex: ADMIN, USER, EDITOR

    @Column(nullable = false)
    private String name;

    //Listado de post creados por un usuario
    @OneToMany(mappedBy = "creador", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<PostDetailsModel> postList = new HashSet<>();


    public UserModel(String username, String password, String role, String name) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
    }

    public UserModel() {
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PostDetailsModel> getPostList() {
        return postList;
    }

    public void setPostList(Set<PostDetailsModel> postList) {
        this.postList = postList;
    }
}
