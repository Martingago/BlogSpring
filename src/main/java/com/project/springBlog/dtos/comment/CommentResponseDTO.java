package com.project.springBlog.dtos.comment;

import java.time.LocalDateTime;

public class CommentResponseDTO {

    private Long id;
    private String contenido;
    private LocalDateTime fechaComentario;
    private Long postId;
    private Long userId;
    private String username;
    private Long originId;
    private Long replyId;
    private long countReplies;

    public CommentResponseDTO(Long id, String contenido, LocalDateTime fechaComentario, Long postId, Long userId, String username, Long replyId, Long originId, long countReplies) {
        this.id = id;
        this.contenido = contenido;
        this.fechaComentario = fechaComentario;
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.replyId = replyId;
        this.originId = originId;
        this.countReplies = countReplies;
    }

    public CommentResponseDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public long getCountReplies() {
        return countReplies;
    }

    public void setCountReplies(long countReplies) {
        this.countReplies = countReplies;
    }
}
