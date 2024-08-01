package com.project.springBlog.dtos;

import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;

public class PublicacionDetailsDTO {

    private PostModel post;
    private PostDetailsModel details;


    public PublicacionDetailsDTO(PostModel post, PostDetailsModel details) {
        this.post = post;
        this.details = details;
    }

    public PostModel getPost() {
        return post;
    }

    public PostDetailsModel getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "PublicacionDetails{" +
                "post=" + post +
                ", details=" + details +
                '}';
    }
}

