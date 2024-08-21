package com.project.springBlog.mapper;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.models.CommentModel;
import com.project.springBlog.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;

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
        dto.setOriginId(comment.getComentarioOrigen().getId());
        if(comment.getComentarioPadre() != null){
            dto.setReplyId(comment.getComentarioPadre().getId());
        }
        return dto;
    }
}
