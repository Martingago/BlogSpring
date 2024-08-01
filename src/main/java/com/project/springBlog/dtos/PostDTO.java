package com.project.springBlog.dtos;

import com.project.springBlog.models.TagModel;

import java.util.Set;

public class PostDTO {

    private long id;

    private String titulo;

    private String contenido;

    private Set<TagModel> tagsList;
}
