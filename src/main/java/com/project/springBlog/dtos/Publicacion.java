package com.project.springBlog.dtos;

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

    @NotNull(message = "Creator cannot be null")
    @NotEmpty(message = "Creator cannot be empty")
    private String creador;
    
    @NotNull(message = "List tags cannot be null")
    private List<Integer> tags = new ArrayList<>();

    public Publicacion(String titulo, String contenido, String creador, List<Integer> tags) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.creador = creador;
        this.tags = tags;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getCreador() {
        return creador;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
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
