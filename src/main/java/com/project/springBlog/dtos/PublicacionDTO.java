package com.project.springBlog.dtos;

import com.project.springBlog.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class PublicacionDTO {

    private String titulo;
    private String contenido;
    private UserModel creador;
    private List<Long> tags = new ArrayList<>();

    public PublicacionDTO(String titulo, String contenido, List<Long> tags) {
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

    public List<Long> getTags() {
        return tags;
    }

    public void setTags(List<Long> tags) {
        this.tags = tags;
    }

}
