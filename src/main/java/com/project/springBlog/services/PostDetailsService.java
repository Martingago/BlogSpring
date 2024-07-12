package com.project.springBlog.services;

import com.project.springBlog.exceptions.EntityException;
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
        try {
            return postDetailsRepository.save(postDetails);
        }catch (Exception ex){
            throw new EntityException("Exception during adding post details", ex);
        }

    }

    public PostDetailsModel getPostDetails(long id){
        try {
            Optional<PostDetailsModel> details = postDetailsRepository.findById(id);
            return details.orElse(null);
        }catch (Exception ex){
            throw new EntityException("Exception during getting post details", ex);
        }

    }

    public boolean deletePostDetails(long id){
        if(!postDetailsRepository.existsById(id)){
            return false;
        }
        try {
            postDetailsRepository.deleteById(id);
            return true;
        }catch (Exception ex){
            throw  new EntityException("Error during deleting post details", ex);
        }
    }

}
