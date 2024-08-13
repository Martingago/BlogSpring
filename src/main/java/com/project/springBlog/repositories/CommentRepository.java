package com.project.springBlog.repositories;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.models.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<CommentModel, Long> {

    /**
     * Realiza una busqueda de los comentarios principales asociados a un post
     * @param postId identificador del post sobre el que se van a buscar los comentarios principales
     * @return
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id, c.contenido, c.fechaComentario, c.usuario.id, c.comentarioPadre.id) " +
            "FROM CommentModel c " +
            "WHERE c.postDetail.id = :postId AND c.comentarioPadre IS NULL")
    List<CommentDTO> findMainCommentsByPostId(@Param("postId") Long postId);

//    /**
//     * Selecciona las respuestas a un comentario principal
//     * @param parentId
//     * @return
//     */
//    @Query("WITH RECURSIVE ComentariosCTE AS (" +
//            "    SELECT c.id, c.idPost, c.idUsuario, c.mensaje, c.fechaCreacion, c.idComentarioPadre " +
//            "    FROM CommentModel c " +
//            "    WHERE c.idComentarioPadre = :parentId " +
//            "    UNION ALL " +
//            "    SELECT c.id, c.idPost, c.idUsuario, c.mensaje, c.fechaCreacion, c.idComentarioPadre " +
//            "    FROM CommentModel c " +
//            "    INNER JOIN ComentariosCTE cte ON cte.id = c.idComentarioPadre " +
//            ") " +
//            "SELECT c FROM ComentariosCTE c")
//    List<CommentModel> findRepliesByParentId(@Param("parentId") Long parentId);

    /**
     * Realiza una búsqueda de todos los comentarios asociados a un post
     * @param postId identificador del post sobre el que se quieren obtener los comentarios
     * @param pageable objeto pageable para devolver paginacion
     * @return Page con los comentarios de una publicacion
     */
    @Query("SELECT new com.project.springBlog.dtos.CommentDTO(c.id, c.contenido, c.fechaComentario, c.usuario.id, c.comentarioPadre.id) " +
            "FROM CommentModel c " +
            "WHERE c.postDetail.id = :postId")
    Page<CommentDTO> findAllCommentsByPostId(@Param("postId") Long postId, Pageable pageable);
}
