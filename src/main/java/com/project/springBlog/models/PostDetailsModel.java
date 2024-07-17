package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="post_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostDetailsModel {
    @Id
    private long id;

    @Column(name="fecha_creacion")
    private Date fechaCreacion;

    @Column(name="created_by")
    private String creador;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private PostModel post;

    public PostDetailsModel(Date fechaCreacion, String creador) {
        this.fechaCreacion = fechaCreacion;
        this.creador = creador;
    }
    public PostDetailsModel(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "PostDetails{" +
                "id=" + id +
                ", fechaCreacion=" + fechaCreacion +
                ", creador='" + creador + '\'' +
                '}';
    }
}
