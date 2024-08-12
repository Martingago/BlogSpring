package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "postDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    // @JsonIgnoreProperties({"usuario", "postDetail", "comentarioPadre", "respuestasComentario"})
    private Set<CommentModel> comentariosList = new HashSet<>();

    public PostDetailsModel(Date fechaCreacion, UserModel creador) {
        this.fechaCreacion = fechaCreacion;
        this.creador = creador;
    }

    public void addComentario(CommentModel coment){
        this.getComentariosList().add(coment);
        coment.setPostDetail(this);
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

    public Set<CommentModel> getComentariosList() {
        return comentariosList;
    }

    public void setComentariosList(Set<CommentModel> comentariosList) {
        this.comentariosList = comentariosList;
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
