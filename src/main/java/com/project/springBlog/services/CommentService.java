package com.project.springBlog.services;

import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.CommentRepository;
import com.project.springBlog.repositories.PostDetailsRepository;
import com.project.springBlog.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * Funcion que crea un comentario en un post por un usuario
     * @param postDetails identificador del post al que pertenece el comentario
     * @param usuario identificador del usuario que ha escrito el post
     * @param contenido String con el contenido del comentario
     * @param comentarioPadreId identificador de otro comentario en caso de ser una respuesta
     * @return newComment generado en la base de datos
     */
    public CommentModel addComentario(PostDetailsModel postDetails, UserModel usuario, String contenido, Long comentarioPadreId) {
        CommentModel comentarioPadre = null;
        if(comentarioPadreId != null){ //Se comprueba si el comentario es una respuesta o no
            comentarioPadre = commentRepository.findById(comentarioPadreId).orElse(null);
        }
            CommentModel newComment = new CommentModel(contenido, LocalDateTime.now(), usuario, postDetails,comentarioPadre); //Se crea el comentario
            commentRepository.save(newComment);
            return newComment;
    }

    /**
     * Realiza una validacion antes de eliminar el comentario de un usuario.
     * Un comentario sólo puede ser eliminado por el usuario creador de dicho comentario o por un administrador
     * @param usuario datos del usuario que realiza la operación
     * @param idComentario a eliminar
     * @return boolean si se ha completado o un error.
     */
    public boolean validateAndDeleteCommentario(UserModel usuario, Long idComentario){
        if(usuario.getId() == idComentario || usuario.getRolesList().contains("ADMIN")){
            return deleteComentario(idComentario);
        }else{
            throw new SecurityException("No permission to do this action");
        }
    }

    /**
     * Elimina un comentario de la base de datos y sus respuestas asociadas
     * @param id
     * @return
     */
    public boolean deleteComentario(Long id){
        Optional<CommentModel> commentOpt = commentRepository.findById(id);
        if(commentOpt.isPresent()){
            CommentModel comentario = commentOpt.get();
            //Elimina todas las respuestas al comentario
            for(CommentModel respuesta : comentario.getRespuestasComentario()){
                deleteComentario(respuesta.getId()); //Se llama a la misma funcion para que continue eliminando comentarios
            }
            commentRepository.delete(comentario);
            return true;
        }else {
            throw  new EntityNotFoundException("Comment with id " + id + "was not founded");
        }
    }


}
