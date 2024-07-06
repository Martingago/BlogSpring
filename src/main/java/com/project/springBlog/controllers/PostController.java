package com.project.springBlog.controllers;

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
    public PostModel addPost(@RequestBody PostModel post) {
        return this.postService.addPost(post);
    }
}
