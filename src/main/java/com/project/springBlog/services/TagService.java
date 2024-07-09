package com.project.springBlog.services;

import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public ArrayList<TagModel> getTags(){
        return (ArrayList<TagModel>) tagRepository.findAll();
    }

    public TagModel addTag(TagModel tag){
        return tagRepository.save(tag);
    }

    public TagModel getTag(long id){
        Optional<TagModel> tagModel = tagRepository.findById(id);
        return tagModel.orElse(null);
    }

    public boolean deleteTag(long id) {
        try {
            tagRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return false; // El tag no existe
        } catch (Exception e) {
            e.printStackTrace();
            // Loguear o manejar de otra manera la excepción
            return false; // Otro error al intentar eliminar el tag
        }
    }
}
