package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.BatchSize;

import java.util.*;

@Entity
@Table(name="posts")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "title of the post cannot be empty")
    private String titulo;

    @Column(columnDefinition="LONGTEXT")
    @NotEmpty(message = "Content of the post cannot be empty")
    private String contenido;

    /**
     * Es la parte propietaria de la relación entre post y tags (posts_tags) la eliminación de un post es gestionada
     * dentro de tags por la propia sesión JPA(Hibernate), no es necesario un @Preremove cuando se elimine un post
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "posts_tags",
            joinColumns = {@JoinColumn(name="post_id")},
            inverseJoinColumns = {@JoinColumn(name="tag_id")}
    )
    private Set<TagModel> tagList = new HashSet<>();


    /**
     * Añade una tag al post y tambien añade un post al tag referenciado
     * @param tag
     */
    public void  addTag(TagModel tag){
        this.tagList.add(tag);
        tag.getPostsList().add(this);
    }

    /**
     * Elimina una tag del post y tambien elimina un post del tag referenciado
     * @param tag
     */
    public void deteleTag(TagModel tag){
        this.tagList.remove(tag);
        tag.getPostsList().remove(this);
    }

    //Contructor

    public PostModel(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public PostModel(){}

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

    public Set<TagModel> getTagList() {
        return tagList;
    }

    public void setTagList(Set<TagModel> tagList) {
        this.tagList = tagList;
    }

}
