package com.project.springBlog.controllers;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping
    public PublicacionDetails addPost(@RequestBody Publicacion publicacion) {
        return postService.addPublicacion(publicacion);
    }

}
