package com.project.springBlog.dtos;

import com.project.springBlog.models.PostDetailsModel;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicacionDetails {
    private long postId;
    private String titulo;
    private String contenido;
    private String creador;
    private Date fechaPublicacion;
    private List<TagModel> tags;

    public PublicacionDetails(PostModel post, PostDetailsModel details) {
        this.postId = post.getId();
        this.titulo = post.getTitulo();
        this.contenido = post.getContenido();
        this.creador = details.getCreador();
        this.fechaPublicacion = details.getFechaCreacion();

        this.tags = new ArrayList<>();
        for (TagModel tag : post.getTagList()) {
            TagModel tagDTO = new TagModel();
            tagDTO.setId(tag.getId());
            tagDTO.setNombre(tag.getNombre());
            this.tags.add(tagDTO);
        }
    }

}

