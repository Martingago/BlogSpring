package com.project.springBlog.services;

import com.project.springBlog.models.PostModel;
import com.project.springBlog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public ArrayList<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public PostModel addPost(PostModel post){
        return postRepository.save(post);
    }


}
