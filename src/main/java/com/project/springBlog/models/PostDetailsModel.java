package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.springBlog.dtos.CreatorInfoDTO;
import jakarta.persistence.*;
import org.apache.catalina.User;

import java.util.Date;

@Entity
@Table(name="post_details")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PostDetailsModel {
    @Id
    private long id;

    @Column(name="fecha_creacion")
    private Date fechaCreacion;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="created_by", referencedColumnName = "id")
    private UserModel creador;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private PostModel post;

    public PostDetailsModel(Date fechaCreacion, UserModel creador) {
        this.fechaCreacion = fechaCreacion;
        this.creador = creador;
    }

    //Estructura de la informacion que obtiene del creador
    @JsonGetter("creador")
    public CreatorInfoDTO serializedCreador(){
        return new CreatorInfoDTO(creador.getId(), creador.getUsername(), creador.getName());
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

    public UserModel getCreador() {
        return creador;
    }

    public void setCreador(UserModel creador) {
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
