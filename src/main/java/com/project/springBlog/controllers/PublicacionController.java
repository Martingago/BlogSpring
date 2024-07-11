package com.project.springBlog.controllers;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.services.PostService;
import com.project.springBlog.services.PublicacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/post")
public class PublicacionController {
    @Autowired
    PublicacionService publicacionService;

    //Código provisional, voy a necesitar obtener los Publicaciones
    @Autowired
    PostService postService;
    //Código provisional, voy a necesitar obtener los Publicaciones
    @GetMapping
    public ArrayList<PostModel> getPublicaciones(){
        return postService.getPosts();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPublicacion(@PathVariable("id") long id){
        PublicacionDetails publicacion =  publicacionService.getPublicacionDetails(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Post was successfully founded", publicacion), HttpStatus.OK);
    }

    @PostMapping
    public PublicacionDetails addPublicacion(@RequestBody Publicacion publicacion) {
        return publicacionService.addPublicacion(publicacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removePublicacion(@PathVariable("id") long id){
        if(publicacionService.deletePublicacion(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Publicacion eliminada con éxito");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado la publicación");
        }
    }

}
