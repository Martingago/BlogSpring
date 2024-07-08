package com.project.springBlog.dtos;

import java.util.ArrayList;
import java.util.List;

public class Publicacion {
    private String titulo;
    private String contenido;
    private String creador;
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
