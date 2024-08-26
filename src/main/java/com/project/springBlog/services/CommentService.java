package com.project.springBlog.services;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.CommentRepository;
import com.project.springBlog.utils.SortUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class CommentService {

    @Autowired

    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    SortUtils sortUtils;

    /**
     * Busca un comentario por su ID y maneja el posible error en caso de no encontrarlo
     * @param id del comentario a buscar
     * @return CommentModel con la información de dicho comentario
     */
    public CommentModel findCommentById(Long id){
        Optional<CommentModel> commentOpt = commentRepository.findById(id);
        if(!commentOpt.isPresent()){
            throw  new EntityNotFoundException("Comment with id "+ id + " was not founded");
        }
        return commentOpt.get();
    }

    /**
     * Busca un comentario por su ID en la base de datos y maneja el posible error de no encontrarlo
     * A diferencia de la función anterior, esta función devuelve un CommentDTO que contiene otra información
     * específica del comentario como el número de respuestas asociadas al comentario.
     * Se usa para enviar al front información específica de un comentario
     * @param id del comentario a obtener información
     * @return CommentDTO con la información detallada de un comentario
     */
    public CommentDTO getCommentData(long id){
        return commentRepository.getCommentById(id).orElseThrow(
                () -> new EntityNotFoundException("Comment with id " + id + " was not founded")
        );
    }

    /**
     * Obtiene paginacion de los comentarios principales de un post
     * @param postId identificador del post sobre el que se quieren obtener los comentarios prinicipales
     * @param pageable
     * @return página con objetos CommentDTO de cada comentario a mostrar
     */
   public Page<CommentDTO> getCommentsFromPost(long postId, Pageable pageable){
       return commentRepository.findMainCommentsByPostId(postId, pageable);
   }

    /**
     * Obtiene las respuestas totales existentes a un comentario específico
     * @param commentId
     * @param pageable
     * @return
     */
   public Page<CommentDTO> getAllRepliesFromComment(long commentId, Pageable pageable){
       return commentRepository.findAllRepliesToComment(commentId, pageable);
   }

    /**
     * Obtiene respuestas directas existentes a un comentario especifico
     * @param commentId
     * @param pageable
     * @return
     */
   public Page<CommentDTO> getDirectRepliesFromComment(long commentId, Pageable pageable){
       return commentRepository.findDirectRepliesToComment(commentId, pageable);
   }

    /**
     * Funcion que crea un comentario en un post por un usuario
     * @param postDetails del post al que pertenece el comentario
     * @param usuario del usuario que ha escrito el post
     * @param contenido String con el contenido del comentario
     * @param comentarioPadreId identificador de otro comentario en caso de ser una respuesta
     * @return newComment generado en la base de datos
     */
    public CommentModel addComentario(PostDetailsModel postDetails, UserModel usuario, String contenido, Long comentarioPadreId) {
        CommentModel newComment = new CommentModel(); //Comentario a publicar
        newComment.setPostDetail(postDetails);
        newComment.setUsuario(usuario);
        newComment.setContenido(contenido);
        newComment.setFechaComentario(LocalDateTime.now());
        //El comentario se trata de una respuesta a otro comentario
        if(comentarioPadreId != null){
           CommentModel comentarioPadre = commentRepository.findById(comentarioPadreId).orElseThrow(
                    () ->new EntityNotFoundException("The comment does not exists or it was removed"));
            //Establece el comentario padre (Se trata de una respuesta)
           newComment.setComentarioPadre(comentarioPadre);

           //Establece el origen de las respuestas
            newComment.setComentarioOrigen(
                    comentarioPadre.getComentarioOrigen() != null ? comentarioPadre.getComentarioOrigen() //Si no es null se obtiene el origen de la respuesta
                            : comentarioPadre //null el comentario al que se responde es el origen
            );
        }
            return commentRepository.save(newComment);
        }

    /**
     * Realiza una validacion antes de eliminar el comentario de un usuario.
     * Un comentario sólo puede ser eliminado por el usuario creador de dicho comentario o por un administrador
     * @param idComentario a eliminar
     * @return boolean si se ha completado o un error.
     */
    public boolean validateAndDeleteCommentario(Long idComentario) {
        //Busca el comentario en la BBDD - Si no existe hace un throw EntityNotFound
        CommentModel comentario = findCommentById(idComentario);
        //Si es un usuario administrador elimina el comentario
        if (securityService.isAdmin()) {
            return deleteComentario(comentario);
        }
        // Obtiene usuario authenticado
        Long userId = securityService.getUserModelId();
        UserModel usuario = userService.findUserById(userId);

        if (usuario.getId().equals(comentario.getUsuario().getId())) {
            return deleteComentario(comentario);
        } else {
            throw new SecurityException("No permission to delete this comment");
        }

    }

    /**
     * Elimina un comentario de la base de datos
     * @param comentario modelo del comentario a eliminar
     * @return boolean indicando el exito de la operación
     */
    public boolean deleteComentario(CommentModel comentario) {
        //Se elimina el comentario padre
        try {
            commentRepository.deleteById(comentario.getId());
            return true;
        }catch (Exception ex){
            System.err.println("Error al eliminar comentario: " + ex);
            return false;
        }
    }

}
