package com.project.springBlog.controllers;

import com.project.springBlog.config.CustomUserDetails;
import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.dtos.comment.CommentResponseDTO;
import com.project.springBlog.mapper.CommentMapper;
import com.project.springBlog.models.CommentModel;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import com.project.springBlog.repositories.UserRepository;
import com.project.springBlog.services.CommentService;
import com.project.springBlog.services.PostDetailsService;
import com.project.springBlog.services.PostService;
import com.project.springBlog.utils.SortUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private SortUtils sortUtils;

    @Autowired
    private PostDetailsService detailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostDetailsRepository postDetailsRepository;

    /**
     * Obtiene un ResponseEntity con el estado del servicio + paginación de comentarios principales de una publicación
     * @param id identificador del post sobre el que se quieren cargar los comentarios
     * @param page página de la que se quieren extraer los comentarios
     * @param size tamaño de las páginas
     * @return
     */
    @GetMapping("public/post/{id}/comments")
    public ResponseEntity<ResponseDTO> getCommentsFromPost(
            @PathVariable("id") long id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        Pageable pageable=  sortUtils.createPageable(page, size);

        Page<CommentResponseDTO> pageCommentsFromPost = commentService.getCommentsFromPost(id,pageable);

        return new ResponseEntity<>(new ResponseDTO(true, "List of post comments", pageCommentsFromPost), HttpStatus.OK);
    }

    /**
     * Obtiene la información de un comentario pasado como id
     * @param id del comentario sobre el que se quieren obtener los datos
     * @return ResponseEntity con el CommentDTO con la información del comentario
     */
    @GetMapping("public/comments/{id}")
    public ResponseEntity<ResponseDTO> getCommentById(
            @PathVariable("id") long id
    ){
        CommentResponseDTO commentResponseDTO = commentService.getCommentData(id);
        return  new ResponseEntity<>( new ResponseDTO(true, "Comment was successfully founded", commentResponseDTO), HttpStatus.OK);
    }

    /**
     * Obtiene la información de las respuestas directas a un comentario
     * @param id del comentario sobre el que se quieren obtener las respuestas
     * @return objeto page con las respuestas existentes a un comentario
     */
    @GetMapping("public/comments/{id}/replies")
    public ResponseEntity<ResponseDTO> getCommentReplies(
            @PathVariable("id") long id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        Pageable pageable = sortUtils.createPageable(page, size);

        Page<CommentResponseDTO> respuestasDTO = commentService.getDirectRepliesFromComment(id, pageable);
        return new ResponseEntity<>(new ResponseDTO(true, "List of direct replies to a comment", respuestasDTO), HttpStatus.OK);
    }

    /**
     * Obtiene la información de las respuestas totales existentes a un comentario
     * @param id del comentario sobre el que se quieren obtener las respuestas
     * @return objeto page con las respuestas existentes a un comentario
     */
    @GetMapping("public/comments/{id}/replies/all")
    public ResponseEntity<ResponseDTO> getAllCommentReplies(
            @PathVariable("id") long id,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size)
    {

        Pageable pageable = sortUtils.createPageable(page, size);

        Page<CommentResponseDTO> respuestasDTO = commentService.getAllRepliesFromComment(id, pageable);
        return new ResponseEntity<>(new ResponseDTO(true, "List of all replies to a comment", respuestasDTO), HttpStatus.OK);
    }

    /**
     * Añade un comentario a una publicación
     * @param postId identificador del post sobre el que se añade un comentario
     * @param comentario DTO con la información a añadir a la base de datos
     * @return DTO con otros datos adicionales sobre el comentario añadido
     */
    @PostMapping("/user/post/{id}/comment")
    @Transactional
    public ResponseEntity<ResponseDTO> addComentario(@PathVariable("id") long postId,
                                                     @Valid
                                                     @RequestBody CommentDTO comentario) {
        //Obtener credenciales del usuario
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserModel usuario = userRepository.findById(userDetails.getId()).get();

        //Obtiene los datos del post - En caso de no existir lanza un throw EntityNotFound
        PostDetailsModel details = detailsService.getPostDetails(postId);

        //Se añade el comentario:
        CommentModel newComment = commentService.addComentario(details, usuario, comentario.getContenido(), comentario.getReplyId());

        //Se envía un DTO con la información del comentario
        CommentResponseDTO commentResponseDTO = CommentMapper.toResponseDTO(newComment);
        return new ResponseEntity<>(new ResponseDTO(true, "Comment added successfully", commentResponseDTO), HttpStatus.OK);
    }

    /**
     * Elimina un comentario de la base de datos. Los comentarios como respuesta a este comentario también serán eliminados
     * @param id identificador del comentario a eliminar
     * @return ResponseEntity indicando el estado del servicio
     */
    @DeleteMapping("/user/comment/{id}")
    @Transactional
    public ResponseEntity<ResponseDTO> eliminarComentario(@PathVariable("id") long id) {
        commentService.validateAndDeleteCommentario(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Comment was successfully deleted", null), HttpStatus.OK);
    }

}
