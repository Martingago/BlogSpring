package com.project.springBlog.services;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    TagService tagService;

    public ArrayList<PostModel> getPosts(){
        return (ArrayList<PostModel>) postRepository.findAll();
    }

    public PostModel getPost(long id){
        Optional<PostModel>  post=  postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        }else{
            throw new EntityNotFoundException("Post with id: " + id + " was not founded");
        }
    }

    public PostModel addPost(PostModel post){
        return postRepository.save(post);
    }


    public boolean deletePost(long id){
        if(!postRepository.existsById(id)){
            throw new EntityNotFoundException("Error, post was not found");
        }
        try{
            postRepository.deleteById(id);
            return true;
        }
        catch (Exception ex){
            throw  new EntityException("Error during deleting post ", ex);
        }
    }

    public void insertTagsList(PostModel post, List<Integer> etiquetas){
        if(etiquetas != null && !etiquetas.isEmpty()){
            for(Integer id : etiquetas){
                try {
                    TagModel tag = tagService.getTag(id);
                    post.addTag(tag);
                }catch (EntityNotFoundException ex){
                    System.out.println("Tag with id:" + id + " was not found, skipping that one");
                }
            }
        }
    }

}
