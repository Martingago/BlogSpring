package com.project.springBlog.controllers;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
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
    TagService tagService;

    @GetMapping
    public ArrayList<PostModel> getPosts(){
        return postService.getPosts();
    }

    @PostMapping
    public PostModel addPost(@RequestBody Publicacion publicacion) {
        //Se crea el contenido del post
        PostModel post = new PostModel(publicacion.getTitulo(), publicacion.getContenido());

        //Se crean los postDetails
        PostDetailsModel details = new PostDetailsModel(new Date(), publicacion.getCreador());
        details.setPost(post);

        //Se añaden las tags:
        List<Integer> etiquetas = publicacion.getTags();

        for(Integer tagId : etiquetas){
            Optional<TagModel> tagOp = tagService.getTag(tagId);
            tagOp.ifPresent(tag -> post.addTag(tag));
        }

        return this.postService.addPost(post);
    }
}
