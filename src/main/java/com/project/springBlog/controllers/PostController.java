package com.project.springBlog.controllers;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;


@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping
    public ArrayList<PostModel> getPosts(){
        return postService.getPosts();
    }

    //Obtiene los datos de una publicacion
    @GetMapping("/{id}")
    public PublicacionDetails getPublicacion(@PathVariable("id") long id){
        return postService.getPublicacionDetails(id);
    }

    @PostMapping
    public PublicacionDetails addPost(@RequestBody Publicacion publicacion) {
        return postService.addPublicacion(publicacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePublicacion(@PathVariable("id") long id){
        if(postService.deletePublicacion(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Publicacion eliminada con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado la publicación");
        }
    }

}
