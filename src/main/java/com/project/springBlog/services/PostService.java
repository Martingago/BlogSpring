package com.project.springBlog.services;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostDetailsService detailsService;

    @Autowired
    TagService tagService;


    public ArrayList<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public PostModel addPost(PostModel post){
        return postRepository.save(post);
    }

    public PostModel addPublicacion(Publicacion publicacion){
        //Se crea el contenido del post
        PostModel post = new PostModel(publicacion.getTitulo(), publicacion.getContenido());

        //Se añaden las tags:
        List<Integer> etiquetas = publicacion.getTags();

        for(Integer tagId : etiquetas){
            TagModel tag = tagService.getTag(tagId);
            post.addTag(tag);
        }
        post = addPost(post);

        //Se crean los postDetails
        PostDetailsModel details = new PostDetailsModel(new Date(), publicacion.getCreador());
        details.setPost(post);
        detailsService.addPostDetails(details);


        return post;
    }


}
