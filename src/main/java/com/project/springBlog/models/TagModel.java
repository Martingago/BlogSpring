package com.project.springBlog.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="tags")
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
    private Set<PostModel> modelList;

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

    public Set<PostModel> getModelList() {
        return modelList;
    }

    public void setModelList(Set<PostModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public String toString() {
        return "TagModel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
