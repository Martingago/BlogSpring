package com.project.springBlog.mapper;

import com.project.springBlog.dtos.TagDTO;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;

public class TagMapper {
    /**
     * Convierte un Model de tag en un DTO de tag con la información básia de los
     * @param tag TagModel a convertir en TagDTO
     * @return TagDTO que incluye la información de la tag + post que contienen dicha Tag
     */
    public static TagDTO toDTO(TagModel tag){
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setNombre(tag.getNombre());

        if(!tag.getPostsList().isEmpty()){
            for(PostModel post : tag.getPostsList()){
                dto.getPostList().add(post.getId());
            }
        }
        return dto;
    }

    /**
     * Convierte un Model de tag en la información básica de una Tag (Id + nombre)
     * @param tag TagModel a convertir en simpleDTO
     * @return tagDTO simple con atributos de id + nombre
     */
    public static TagDTO toSimpleDTO(TagModel tag){
        return new TagDTO(tag.getId(), tag.getNombre());
    }
}
