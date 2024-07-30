package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tags")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TagModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Error name tag cannot be null")
    @NotBlank(message = "Error name tag cannot be blank")
    private String nombre;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "tagList"
    )
    @JsonIgnoreProperties("tagList")
    private Set<PostModel> postsList = new HashSet<>();

    /**
     * Atributos de los post que se enviarán en el JSON cuando se obtenga la información de los tags
     * @return Set con los IDs de los post asociados a cada Tag
     */
    @JsonGetter("postsList")
    public Set<Object> serializedPostsList() {
        Set<Object> serializedPosts = new HashSet<>();
        for (PostModel post : postsList) {
            serializedPosts.add(post.getId());
        }
        return serializedPosts;
    }

    /**
     * Es necesario ya que TagModel es la parte inversa de la relación posts_tags
     * Esta funcion se emplea antes de  eliminar tags.
     * Realiza una busqueda de todos los post que contienen una referencia al tag y la elimina
     */
    @PreRemove
    private void preRemove() {
        Set<PostModel> posts = new HashSet<>(postsList); // Crear una copia de la colección
        for (PostModel post : posts) {
            post.deteleTag(this); // Actualiza la lista de tags en PostModel
        }
    }

    //Constructor

    public TagModel(String nombre) {
        this.nombre = nombre;
    }

    public TagModel(){}

    // Getters and setterss
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

    public Set<PostModel> getPostsList() {
        return postsList;
    }

    public void setPostsList(Set<PostModel> modelList) {
        this.postsList = modelList;
    }

}
