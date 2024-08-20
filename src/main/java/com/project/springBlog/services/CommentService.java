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
    private CommentRepository commentRepository;

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
     * @param page número de pagina de la cual extraer los comentarios
     * @param size tamaño de elementos mostrados por página
     * @return página con objetos CommentDTO de cada comentario a mostrar
     */
   public Page<CommentDTO> getCommentsFromPost(long postId, int page, int size){
       int pageSize = sortUtils.maxLimitsizePage(size); //Comprueba el valor máximo de página
       Pageable pageable = PageRequest.of(page, pageSize);
       return commentRepository.findMainCommentsByPostId(postId, pageable);
   }

    /**
     * Obtiene las respuestas totales existentes a un comentario específico
     * @param commentId
     * @param page
     * @param size
     * @return
     */
   public Page<CommentDTO> getAllRepliesFromComment(long commentId, int page, int size){
       int pageSize = sortUtils.maxLimitsizePage(size); //Comprueba valor máximo de página
       Pageable pageable = PageRequest.of(page,pageSize);
       return commentRepository.findAllRepliesToComment(commentId, pageable);
   }

    /**
     * Obtiene respuestas directas existentes a un comentario especifico
     * @param commentId
     * @param page
     * @param size
     * @return
     */
   public Page<CommentDTO> getDirectRepliesFromComment(long commentId, int page, int size){
       int pageSize = sortUtils.maxLimitsizePage(size);
       Pageable pageable = PageRequest.of(page, pageSize);
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
        if(comentarioPadreId != null){ //Se comprueba si el comentario es una respuesta o no
           CommentModel comentarioPadre = commentRepository.findById(comentarioPadreId).orElseThrow(
                    () ->new EntityNotFoundException("The comment does not exists or it was removed"));
           newComment.setComentarioPadre(comentarioPadre); //Establece el comentario padre (Se trata de una respuesta)
            newComment.setComentarioOrigen(comentarioPadre.getComentarioOrigen()); //Establece cual es el origen principal de un comentario
        }else{
            newComment.setComentarioOrigen(newComment); //Establece el comentario origen como la raiz
        }
            return commentRepository.save(newComment);
        }

    /**
     * Realiza una validacion antes de eliminar el comentario de un usuario.
     * Un comentario sólo puede ser eliminado por el usuario creador de dicho comentario o por un administrador
     * @param usuario datos del usuario que realiza la operación
     * @param idComentario a eliminar
     * @return boolean si se ha completado o un error.
     */
    public boolean validateAndDeleteCommentario(UserModel usuario, Long idComentario) {
        CommentModel comentario = null;
        //Busca el comentario en la BBDD
        comentario = findCommentById(idComentario);

        if (usuario.getId() == comentario.getUsuario().getId() || usuario.getRoles().contains("ADMIN")) {
            return deleteComentario(comentario);
        } else {
            throw new SecurityException("No permission to delete this comment");
        }
    }

    /**
     * Elimina un comentario y sus respuestas pasando como parámetro el CommnetModel
     * @param comentario a eliminar
     * @return
     */
    public boolean deleteComentario(CommentModel comentario) {
        //Se eliminan las respuestas de un comentario
        for (CommentModel respuesta : comentario.getRespuestasComentario()) {
            try {
                //Elimina de manera recursiva las respuestas
                deleteComentario(respuesta.getId());
            } catch (EntityNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
        //Se elimina el comentario padre
        try {
            commentRepository.deleteById(comentario.getId());
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    /**
     * Elimina un comentario de la base de datos y sus respuestas asociadas
     * @param id comentario a eliminar
     * @return
     */
    public boolean deleteComentario(Long id){
        return deleteComentario(findCommentById(id));
    }
}
