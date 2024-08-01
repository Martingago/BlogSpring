package com.project.springBlog.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashSet;
import java.util.Set;

public class TagDTO {

    private long id;
    private String nombre;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Long> postList;

    public TagDTO(long id, String nombre, Set<Long> postList) {
        this.id = id;
        this.nombre = nombre;
        this.postList = postList;
    }

    public TagDTO(long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public TagDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Long> getPostList() {
        return postList;
    }

    public void setPostList(Set<Long> postList) {
        this.postList = postList;
    }
}
