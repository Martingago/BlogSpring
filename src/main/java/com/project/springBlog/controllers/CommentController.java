package com.project.springBlog.controllers;

import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import com.project.springBlog.repositories.UserRepository;
import com.project.springBlog.services.CommentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    @PostMapping("/user/post/{id}/comment")
    @Transactional
    public ResponseEntity<ResponseDTO> addComentario(@PathVariable Long postId,
                                                     @RequestBody CommentModel comentario) {
        try {
            //Validar que el usuario exista:
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<UserModel> authUser = userRepository.findByUsername(username);
            if (!authUser.isPresent()) {
                return new ResponseEntity<>(new ResponseDTO(false, "User not authenticated", null), HttpStatus.FORBIDDEN);
            }
            UserModel usuario = authUser.get();

            //Comprobar que el post exista:
            Optional<PostDetailsModel> postOpt = postDetailsRepository.findById(postId);
            if (!postOpt.isPresent()) {
                return new ResponseEntity<>(new ResponseDTO(false, "Post not founded", null), HttpStatus.NOT_FOUND);
            }
            PostDetailsModel details = postOpt.get();

            //Se añade el comentario:
            CommentModel newComment = commentService.addComentario(details, usuario, comentario.getContenido(), 2L);

        } catch ()
    }


    @DeleteMapping("/user/comment/{id}")
    @Transactional
    public ResponseEntity<ResponseDTO> eliminarComentario(@PathVariable Long id) {
        try {
            //Obtiene los datos del usuario que va a realizar la acción
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<UserModel> authUser = userRepository.findByUsername(username);
            if (!authUser.isPresent()) {
                return new ResponseEntity<>(new ResponseDTO(false, "User not authenticated", null), HttpStatus.UNAUTHORIZED);
            }
            UserModel usuario = authUser.get();
            //Valida y elimina el comentario
            commentService.validateAndDeleteCommentario(usuario, id);
            return new ResponseEntity<>(new ResponseDTO(true, "Comment was successfully deleted", null), HttpStatus.OK);
        } catch (SecurityException ex) {
            return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage(), null), HttpStatus.FORBIDDEN);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage(), null), HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<>(new ResponseDTO(false, ex.getMessage(), null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
