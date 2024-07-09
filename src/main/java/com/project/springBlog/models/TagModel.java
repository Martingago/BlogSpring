package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="tags")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TagModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            mappedBy = "tagList"
    )
    @JsonIgnoreProperties("tagList")
    private Set<PostModel> postsList = new HashSet<>();

    @JsonGetter("postsList")
    public Set<Object> serializedPostsList() {
        Set<Object> serializedPosts = new HashSet<>();
        for (PostModel post : postsList) {
            serializedPosts.add(post.getId());
        }
        return serializedPosts;
    }

    //Constructor getters and setters

    public TagModel(String nombre) {
        this.nombre = nombre;
    }

    public TagModel(){}

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

    @Override
    public String toString() {
        return "TagModel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
