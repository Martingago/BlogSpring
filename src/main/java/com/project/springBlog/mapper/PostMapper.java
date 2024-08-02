package com.project.springBlog.mapper;

import com.project.springBlog.dtos.PostDTO;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.models.TagModel;

import java.util.HashSet;
import java.util.Set;

public class PostMapper {

    /**
     * Devuelve un postDTO que contiene a su vez el listado de los tags en formato TagDTO
     * @param post
     * @return PostDTO que contiene listado de tagsDTO
     */
    public static PostDTO toDTO(PostModel post){
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitulo(post.getTitulo());
        dto.setContenido(post.getContenido());
        dto.setTagsList(new HashSet<>()); //Se establece el set como vacio
        //Se rellena el set con los datos de los tagsDTO:
        Set<TagModel> tagsList = post.getTagList();
        if(!tagsList.isEmpty()){
            for(TagModel tag : tagsList){
                dto.getTagsList().add(TagMapper.toSimpleDTO(tag)); //Se añade el tag DTO
            }
        }
        return dto;
    }

}
