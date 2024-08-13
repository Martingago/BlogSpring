package com.project.springBlog.repositories;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.models.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository  extends JpaRepository<CommentModel, Long> {

    /**
     * Realiza una búsqueda de todos los comentarios asociados a un post
     * @param postId identificador del post sobre el que se quieren obtener los comentarios
     * @return Set con el DTO de los comentarios
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id,c.contenido,c.fechaComentario,c.usuario.id,c.comentarioPadre.id) " +
            "FROM CommentModel c " +
            "WHERE c.postDetail.id = :postId" )
    Set<CommentDTO> findAllCommentsByPostId(@Param("postId") Long postId);
}
