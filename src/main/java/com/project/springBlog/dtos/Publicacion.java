package com.project.springBlog.dtos;

import com.project.springBlog.models.UserModel;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Publicacion {
    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    private String titulo;

    @NotNull(message = "Content cannot be null")
    @NotEmpty(message = "Content cannot be empty")
    private String contenido;

    private UserModel creador;
    
    @NotNull(message = "List tags cannot be null")
    private List<Long> tags = new ArrayList<>();

    public Publicacion(String titulo, String contenido, List<Long> tags) {
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

    @Override
    public String toString() {
        return "Publicacion{" +
                "titulo='" + titulo + '\'' +
                ", contenido='" + contenido + '\'' +
                ", creador='" + creador + '\'' +
                ", tags=" + tags +
                '}';
    }
}
