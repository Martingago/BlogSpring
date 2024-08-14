package com.project.springBlog.services;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.mapper.CommentMapper;
import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    PostDetailsService detailsService;

   public Page<CommentDTO> getCommentsFromPost(long postId, int page, int size){
       Pageable pageable = PageRequest.of(page, size);
       return commentRepository.findAllCommentsByPostId(postId, pageable);
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
     * Busca un comentario por su ID y maneja el posible error en caso de no encontrarlo
     * @param id del comentario a buscar
     * @return
     */
    public CommentModel findCommentById(Long id){
        Optional<CommentModel> commentOpt = commentRepository.findById(id);
        if(!commentOpt.isPresent()){
            throw  new EntityNotFoundException("Comment with id "+ id + " was not founded");
        }
        return commentOpt.get();
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
