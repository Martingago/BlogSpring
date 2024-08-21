package com.project.springBlog.controllers;

import com.project.springBlog.dtos.CommentDTO;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.dtos.UserDTO;
import com.project.springBlog.mapper.UserMapper;
import com.project.springBlog.models.UserModel;
import com.project.springBlog.services.CommentService;
import com.project.springBlog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    /**
     * Obtiene los datos de un usuario tanto a través de su ID, como de su username
     * @return map de userDTO con la informacion de un usuario
     */
    @GetMapping("public/user")
    public ResponseEntity<ResponseDTO> getUser(@RequestParam("identifier") String identifier){
        UserModel userModel;
        try{
            Long id = Long.parseLong(identifier);
            userModel = userService.findUserById(id);
            UserDTO userDTO = UserMapper.toDetailDTO(userModel);
        }catch (NumberFormatException ex){
            userModel = userService.findByUsername(identifier);

        }
        if(userModel != null){
            UserDTO userDTO = UserMapper.toDetailDTO(userModel);
            return new ResponseEntity<>(new ResponseDTO(true, "User succesfully founded", userDTO), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ResponseDTO(false, "User not found", null), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Encuentra y devuelve un listado de comentarios escritos por un usuario especificado
     * @param id identificador del usuario sobre el que buscar los comentarios - id o username
     * @return ResponseDTO que incluye estado de la operacion y listado de comentarios en caso de existir
     */
    @GetMapping("public/user/{id}/comments")
    public ResponseEntity<ResponseDTO> getUserComments(@PathVariable("id") String id){
      UserModel usuario = userService.findUserByIdOrUsername(id); //Busca al usuario en la base de datos
        Set<CommentDTO> setComments = userService.getUserComments(usuario);
        return new ResponseEntity<>(new ResponseDTO(true, "List of user comments found succesfully", setComments), HttpStatus.OK);

    }


}
