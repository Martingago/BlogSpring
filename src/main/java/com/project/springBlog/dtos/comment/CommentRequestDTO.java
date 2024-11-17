package com.project.springBlog.dtos.comment;

public class CommentRequestDTO {
    private String contenido;
    private Long replyId;

    /**
     * DTO validación para añadir un comentario estructura que debe recibir desde el front-end
     * @param contenido
     * @param replyId
     */
    public CommentRequestDTO(String contenido,  Long replyId) {
        this.contenido = contenido;
        this.replyId = replyId;
    }

    public CommentRequestDTO(){}

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }
}
