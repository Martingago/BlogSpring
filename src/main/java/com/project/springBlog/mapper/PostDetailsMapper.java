package com.project.springBlog.mapper;

import com.project.springBlog.dtos.PostDetailsDTO;
import com.project.springBlog.models.PostDetailsModel;

public class PostDetailsMapper {

    public static PostDetailsDTO toDTO(PostDetailsModel details){
        return new PostDetailsDTO(details.getFechaCreacion(), details.getCreador());
    }
}
