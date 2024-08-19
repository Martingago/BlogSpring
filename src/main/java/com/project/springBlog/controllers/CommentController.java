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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    /**
     * Obtiene un ResponseEntity con el estado del servicio + paginacion de comentarios de una publicacion
     * @param id identificador del post sobre el que se quieren cargar los comentarios
     * @param page pagina de la que se quieren extraer los comentarios
     * @param size tamaño de la paginas
     * @return
     */
    @GetMapping("public/post/{id}/comments")
    public ResponseEntity<ResponseDTO> getCommentsFromPost(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<CommentDTO> pageCommentsFromPost = commentService.getCommentsFromPost(id,page,size);

        return new ResponseEntity<>(new ResponseDTO(true, "List of post comments", pageCommentsFromPost), HttpStatus.OK);
    }


    @GetMapping("public/comments/{id}")
    public ResponseEntity<ResponseDTO> getCommentById(
            @PathVariable("id") long id
    ){
        CommentDTO commentDTO = commentService.getCommentData(id);
        return  new ResponseEntity<>( new ResponseDTO(true, "Comentario encontrado", commentDTO), HttpStatus.OK);
    }

    /**
     * Añade un comentario a una publicacion
     * @param postId identificador del post sobre el que se añade un comentario
     * @param comentario DTO con la informacion a añadir a la base de datos
     * @return DTO con otros datos adicionales sobre el comentario añadido
     */
    @PostMapping("/user/post/{id}/comment")
    @Transactional
    public ResponseEntity<ResponseDTO> addComentario(@PathVariable("id") long postId,
                                                     @Valid
                                                     @RequestBody CommentDTO comentario) {
        //Validar que el usuario exista:
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserModel> authUser = userRepository.findByUsername(username);
        if (authUser.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(false, "User not authenticated", null), HttpStatus.FORBIDDEN);
        }
        UserModel usuario = authUser.get();

        //Comprobar que el post exista:
        Optional<PostDetailsModel> postOpt = postDetailsRepository.findById(postId);
        if (postOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(false, "Post not founded", null), HttpStatus.NOT_FOUND);
        }
        PostDetailsModel details = postOpt.get();

        //Se añade el comentario:
        CommentModel newComment = commentService.addComentario(details, usuario, comentario.getContenido(), comentario.getReplyId());

        //Se envia un DTO con la información del comentario
        CommentDTO commentDTO  = CommentMapper.toDTO(newComment);
        return new ResponseEntity<>(new ResponseDTO(true, "Comment addded succesfully", commentDTO), HttpStatus.OK);
    }

    /**
     * Elimina un comentario de la base de datos. Los comentarios como respuesta a este comentario también serán eliminados
     * @param id identificador del comentario a eliminar
     * @return ResponseEntity indicando el estado del servicio
     */
    @DeleteMapping("/user/comment/{id}")
    @Transactional
    public ResponseEntity<ResponseDTO> eliminarComentario(@PathVariable("id") long id) {
        //Obtiene los datos del usuario que va a realizar la acción
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserModel> authUser = userRepository.findByUsername(username);
        if (authUser.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO(false, "User not authenticated", null), HttpStatus.UNAUTHORIZED);
        }
        UserModel usuario = authUser.get();
        //Valida y elimina el comentario
        commentService.validateAndDeleteCommentario(usuario, id);
        return new ResponseEntity<>(new ResponseDTO(true, "Comment was successfully deleted", null), HttpStatus.OK);
    }

}
