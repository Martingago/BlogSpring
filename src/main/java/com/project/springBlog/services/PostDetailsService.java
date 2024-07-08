package com.project.springBlog.services;

import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.repositories.PostDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostDetailsService {
    @Autowired
    PostDetailsRepository postDetailsRepository;

    public PostDetailsModel addPostDetails(PostDetailsModel postDetails){
        return postDetailsRepository.save(postDetails);
    }

}
