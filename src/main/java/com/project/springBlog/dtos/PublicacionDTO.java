package com.project.springBlog.dtos;

import com.project.springBlog.models.UserModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PublicacionDTO {

    private String titulo;
    private String contenido;
    private UserModel creador;
    private Set<Long> tags = new HashSet<>();

    public PublicacionDTO(String titulo, String contenido, Set<Long> tags) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.creador = null;
        this.tags = tags;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public UserModel getCreador() {
        return creador;
    }

    public void setCreador(UserModel creador){ this.creador = creador;}

    public Set<Long> getTags() {
        return tags;
    }

    public void setTags(Set<Long> tags) {
        this.tags = tags;
    }
}
