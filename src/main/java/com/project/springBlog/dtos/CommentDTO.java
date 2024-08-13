package com.project.springBlog.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.UserModel;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String contenido;
    private LocalDateTime fechaComentario;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long postId;
    private Long userId;
    private Long replyId;

    /**
     * DTO salida que se envia al fron-end tras haber generado un comentario
     * @param id
     * @param contenido
     * @param fechaComentario
     * @param userId
     * @param replyId
     */
    public CommentDTO(Long id, String contenido, LocalDateTime fechaComentario, Long userId, Long replyId) {
        this.id = id;
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.userId = userId;
        this.replyId = replyId;
    }

    /**
     * DTO validación para añadir un comentario estructura que debe recibir desde el front-end
     * @param contenido
     * @param replyId
     */
    public CommentDTO(String contenido,  Long replyId) {
        this.contenido = contenido;
        this.replyId = replyId;
    }

    public CommentDTO() {
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

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
