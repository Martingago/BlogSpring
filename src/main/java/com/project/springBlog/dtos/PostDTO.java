package com.project.springBlog.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.springBlog.models.TagModel;

import java.util.Set;

public class PostDTO {

    private long id;

    private String titulo;

    private String contenido;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<TagDTO> tagsList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<Long> tagsIdList;


    /**
     * Constructor DTO para validad el objeto que se recibe desde el front para subir un post
     * @param titulo
     * @param contenido
     * @param tagsIdList
     */
    public PostDTO(String titulo, String contenido, Set<Long> tagsIdList) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.tagsIdList = tagsIdList;
    }

    /**
     * Constructor que se envia al front con la informacion relacionada al post subido
     * @param id
     * @param titulo
     * @param contenido
     * @param tagsList
     */
    public PostDTO(long id, String titulo, String contenido, Set<TagDTO> tagsList) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.tagsList = tagsList;
    }

    public PostDTO() {
    }

    //Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Set<TagDTO> getTagsList() {
        return tagsList;
    }

    public void setTagsList(Set<TagDTO> tagsList) {
        this.tagsList = tagsList;
    }

    public Set<Long> getTagsIdList() {
        return tagsIdList;
    }

    public void setTagsIdList(Set<Long> tagsIdList) {
        this.tagsIdList = tagsIdList;
    }
}
