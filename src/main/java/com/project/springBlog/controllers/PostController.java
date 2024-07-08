package com.project.springBlog.controllers;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.services.PostDetailsService;
import com.project.springBlog.services.PostService;
import com.project.springBlog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public PostModel addPost(@RequestBody Publicacion publicacion) {
        return postService.addPublicacion(publicacion);
    }

}
