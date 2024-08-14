package com.project.springBlog.repositories;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.models.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository  extends JpaRepository<CommentModel, Long> {

    /**
     * Cuenta el número de respuestas que tiene un comentario principal
     * @param comentario
     * @return
     */
    @Query("SELECT COUNT(c) FROM CommentModel c WHERE c.comentarioOrigen = :comentario")
    long countRespuestasComentario(@Param("comentario") CommentModel comentario);

    /**
     * Realiza una búsqueda de todos los comentarios asociados a un post
     * Por cada resultado generado se obtiene un CommentDTO que contiene los datos del comentario + número
     * de respuestas asociados a dicho comentaario
     * @param postId identificador del post sobre el que se quieren obtener los comentarios
     * @param pageable objeto pageable para devolver paginacion
     * @return Page con los comentarios de una publicacion
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id, c.contenido, c.fechaComentario, c.usuario.id, c.comentarioPadre.id, " +
            "COALESCE(COUNT(r) - 1, 0)) " +
            "FROM CommentModel c " +
            "LEFT JOIN c.respuestasComentario r " +
            "WHERE c.postDetail.id = :postId AND c.comentarioPadre IS NULL " +
            "GROUP BY c.id")
    Page<CommentDTO> findAllCommentsByPostId(@Param("postId") Long postId, Pageable pageable);
}
