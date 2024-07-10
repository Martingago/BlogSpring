package com.project.springBlog.services;

import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostDetailsService {
    @Autowired
    PostDetailsRepository postDetailsRepository;

    public PostDetailsModel addPostDetails(PostDetailsModel postDetails){
        return postDetailsRepository.save(postDetails);
    }

    public PostDetailsModel getPostDetails(long id){
        Optional<PostDetailsModel> details = postDetailsRepository.findById(id);
        return details.orElse(null);
    }

    @Transactional
    public boolean deletePostDetails(long id){
        if(!postDetailsRepository.existsById(id)){
            return false;
        }
        try {
            postDetailsRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
