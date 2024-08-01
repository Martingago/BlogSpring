package com.project.springBlog.controllers;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.mapper.CommentMapper;
import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import com.project.springBlog.repositories.UserRepository;
import com.project.springBlog.services.CommentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseDTO> addComentario(@PathVariable("id") long postId,
                                                     @RequestBody CommentDTO comentario) {
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
        CommentModel newComment = commentService.addComentario(details, usuario, comentario.getContenido(), comentario.getReplyId());

        //Se envia un DTO con la información del comentario
        CommentDTO commentDTO = CommentMapper.toDTO(newComment);
        return new ResponseEntity<>(new ResponseDTO(true, "Comment addded succesfully", commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("/user/comment/{id}")
    @Transactional
    public ResponseEntity<ResponseDTO> eliminarComentario(@PathVariable("id") long id) {
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
    }

}
