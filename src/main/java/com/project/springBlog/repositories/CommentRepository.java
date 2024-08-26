package com.project.springBlog.repositories;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.models.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CommentRepository  extends JpaRepository<CommentModel, Long> {

    /**
     * Realiza una busqueda en la base de datos de un comentario a través de su identificador
     * Esta consulta devuelve también el número de respuestas DIRECTAS a este comentario.
     * @param identificador del comentario a buscar
     * @return Optional commentDTO con información del comentario y del número de respuestas asociadas en caso de ser encontrada
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id, c.contenido, c.fechaComentario, c.postDetail.id, c.usuario.id, c.comentarioOrigen.id ,c.comentarioPadre.id," +
            "COUNT(r)) " +
            "FROM CommentModel c " +
            "LEFT JOIN c.respuestasComentario r " +
            "WHERE c.id = :identificador " +
            "GROUP BY c.id")
    Optional<CommentDTO> getCommentById(@Param("identificador")Long identificador);

    /**
     * Realiza una búsqueda de todos los comentarios PRINCIPALES asociados a un post (comentarios que no son una respuesta a otros)
     * Por cada resultado generado se obtiene un CommentDTO que contiene los datos del comentario + número
     * de respuestas asociados a dicho comentario. Respuestas directas e indirectas
     * @param postId identificador del post sobre el que se quieren obtener los comentarios
     * @param pageable objeto pageable para devolver paginacion
     * @return Page con los comentarios de una publicacion
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id, c.contenido, c.fechaComentario, c.usuario.id, c.comentarioOrigen.id, c.comentarioPadre.id, " +
            "COUNT(r)) " +
            "FROM CommentModel c " +
            "LEFT JOIN c.respuestasTotales r " +
            "WHERE c.postDetail.id = :postId AND c.comentarioPadre IS NULL " +
            "GROUP BY c.id")
    Page<CommentDTO> findMainCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

    /**
     * Obtiene las respuestas totales a un comentario en especifico pasado como parámetro
     * @param identificador del comentario sobre el que se quieren obtener TODAS las respuestas
     * @param pageable paginación para devolver con los datos de las respuestas
     * @return Page con los comentarios respuesta al comentario pasado como identificador
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id, c.contenido, c.fechaComentario, c.usuario.id, c.comentarioOrigen.id, c.comentarioPadre.id) " +
            "FROM CommentModel c " +
            "WHERE c.comentarioOrigen.id = :identificador AND c.id <> :identificador")
    Page<CommentDTO> findAllRepliesToComment(@Param("identificador")Long identificador, Pageable pageable);

    /**
     * Obtiene las respuestas directas a un comentario en especifico pasado como identificador
     * @param identificador del comentario sobre el que se quieren obtener las respuestas
     * @param pageable paginacion para devolver los datos de las respuestas
     * @return Page con los comentarios respuesta al comentario pasado como identificador
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id, c.contenido,c.fechaComentario,c.usuario.id, c.comentarioOrigen.id, c.comentarioPadre.id) " +
            "FROM CommentModel c " +
            "WHERE c.comentarioPadre.id = :identificador")
    Page<CommentDTO> findDirectRepliesToComment(@Param("identificador") Long identificador, Pageable pageable);

    /**
     * Cuenta el número de respuestas que tiene un comentario principal
     * @param comentario
     * @return
     */
    @Query("SELECT COUNT(c) FROM CommentModel c WHERE c.comentarioOrigen = :comentario")
    long countRespuestasComentario(@Param("comentario") CommentModel comentario);

}
