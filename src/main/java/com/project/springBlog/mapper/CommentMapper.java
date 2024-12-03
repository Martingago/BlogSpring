package com.project.springBlog.mapper;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.dtos.comment.CommentResponseDTO;
import com.project.springBlog.models.CommentModel;

public class CommentMapper {


    /**
     * Funcion que mapea un CommentModel a un objeto para enviar al front
     * @param comment objeto CommnetModel
     * @return commentDTO con los atributos a enviar al front
     */
    public static CommentDTO toDTO(CommentModel comment){
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContenido(comment.getContenido());
        dto.setFechaComentario(comment.getFechaComentario());
        dto.setUserId(comment.getUsuario().getId());
        dto.setPostId(comment.getPostDetail().getId());

        if(comment.getComentarioOrigen() !=  null){
            dto.setOriginId(comment.getComentarioOrigen().getId());
        }

        if(comment.getComentarioPadre() != null){
            dto.setReplyId(comment.getComentarioPadre().getId());
        }
        return dto;
    }

    public static CommentResponseDTO toResponseDTO(CommentModel commentModel) {
        return new CommentResponseDTO(
                commentModel.getId(),
                commentModel.getContenido(),
                commentModel.getFechaComentario(),
                commentModel.getPostDetail().getId(),
                commentModel.getUsuario().getId(),
                commentModel.getUsuario().getUsername(),
                commentModel.getComentarioPadre() != null ? commentModel.getComentarioPadre().getId() : null,
                commentModel.getComentarioOrigen() != null ? commentModel.getComentarioOrigen().getId() : null,
                commentModel.getRespuestasTotales() != null ? commentModel.getRespuestasTotales().size() : 0
        );
    }
}
