package com.project.springBlog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comentarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 500)
    private String contenido;

    @Column(name = "fecha_comentario")
    private LocalDateTime fechaComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserModel usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostDetailsModel postDetail;

    @ManyToOne(fetch = FetchType.LAZY) //Se utiliza para las respuestas directas
    @JoinColumn(name = "comentario_padre_id")
    private CommentModel comentarioPadre;

    @ManyToOne(fetch = FetchType.LAZY) //Se utiliza para respuestas directas + indirectas
    @JoinColumn(name = "comentario_origen_id")
    private CommentModel comentarioOrigen;

    //Respuestas directas al comentario
    @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentModel> respuestasComentario = new HashSet<>();

    //Respuestas indirectas(Totales) al comentario
    @OneToMany(mappedBy = "comentarioOrigen", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CommentModel> respuestasTotales = new HashSet<>();

    //Constructores

    public CommentModel(String contenido, LocalDateTime fechaComentario, UserModel usuario, PostDetailsModel post, CommentModel comentarioPadre) {
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.usuario = usuario;
        this.postDetail = post;
        this.comentarioPadre = comentarioPadre;
    }

    public CommentModel() {
    }

    //Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public UserModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UserModel usuario) {
        this.usuario = usuario;
    }

    public PostDetailsModel getPostDetail() {
        return postDetail;
    }

    public void setPostDetail(PostDetailsModel postDetail) {
        this.postDetail = postDetail;
    }

    public CommentModel getComentarioPadre() {
        return comentarioPadre;
    }

    public void setComentarioPadre(CommentModel comentarioPadre) {
        this.comentarioPadre = comentarioPadre;
    }

    public Set<CommentModel> getRespuestasComentario() {
        return respuestasComentario;
    }

    public void setRespuestasComentario(Set<CommentModel> respuestasComentario) {
        this.respuestasComentario = respuestasComentario;
    }

    public CommentModel getComentarioOrigen() {
        return comentarioOrigen;
    }

    public void setComentarioOrigen(CommentModel comentarioOrigen) {
        this.comentarioOrigen = comentarioOrigen;
    }

    public Set<CommentModel> getRespuestasTotales() {
        return respuestasTotales;
    }

    public void setRespuestasTotales(Set<CommentModel> respuestasTotales) {
        this.respuestasTotales = respuestasTotales;
    }
}
