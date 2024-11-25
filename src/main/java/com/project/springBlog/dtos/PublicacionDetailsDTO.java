package com.project.springBlog.dtos;

import com.project.springBlog.mapper.PostDetailsMapper;
import com.project.springBlog.mapper.PostMapper;
import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;

public class PublicacionDetailsDTO {

    private PostDTO post;
    private PostDetailsDTO postDetails;

    //Añadir los que serán los comentarios que tiene la publicación.

    public PublicacionDetailsDTO(PostModel post, PostDetailsModel details) {
        this.post =  (post != null) ? PostMapper.toDTO(post) : new PostDTO();
        this.postDetails = (details != null) ? PostDetailsMapper.toDTO(details) : new PostDetailsDTO();
    }

    public PublicacionDetailsDTO(PostDTO post, PostDetailsDTO postDetails) {
        this.post = post;
        this.postDetails = postDetails;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    public PostDetailsDTO getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(PostDetailsDTO postDetails) {
        this.postDetails = postDetails;
    }
}

