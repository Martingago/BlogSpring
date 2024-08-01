package com.project.springBlog.dtos;

import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.UserModel;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String contenido;
    private LocalDateTime fechaComentario;
    private Long postId;
    private Long userId;
    private Long replyId;

    public CommentDTO(Long id, String contenido, LocalDateTime fechaComentario, Long postId, Long userId, Long replyId) {
        this.id = id;
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.postId = postId;
        this.userId = userId;
        this.replyId = replyId;
    }

    public CommentDTO(String comentario, LocalDateTime fechaComentario, Long replyId) {
        this.contenido = comentario;
        this.fechaComentario = fechaComentario;
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
