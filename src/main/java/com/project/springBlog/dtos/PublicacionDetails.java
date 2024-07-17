package com.project.springBlog.dtos;

import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicacionDetails {

    private PostModel post;
    private PostDetailsModel details;


    public PublicacionDetails(PostModel post, PostDetailsModel details) {
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

