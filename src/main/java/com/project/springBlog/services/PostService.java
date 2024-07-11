package com.project.springBlog.services;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public ArrayList<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public PostModel getPost(long id){
        Optional<PostModel>  post=  postRepository.findById(id);
        return post.orElse(null);
    }

    public PostModel addPost(PostModel post){
        return postRepository.save(post);
    }

    @Transactional
    public boolean deletePost(long id){
        if(!postRepository.existsById(id)){
            return false;
        }
        try{
            postRepository.deleteById(id);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}
