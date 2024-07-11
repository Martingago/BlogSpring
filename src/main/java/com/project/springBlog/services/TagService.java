package com.project.springBlog.services;

import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.exceptions.EntityException;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;


    public ArrayList<TagModel> getTags() {
        return (ArrayList<TagModel>) tagRepository.findAll();
    }

    public TagModel getTag(long id) {
        try {
            Optional<TagModel> tagModel = tagRepository.findById(id);
            return tagModel.orElseThrow(() -> new EntityNotFoundException("Tag was not found"));
        } catch (Exception e) {
            throw new RuntimeException("Exeption during getting tag " + e);
        }
    }

    public TagModel addTag(TagModel tag) {
        try {
            TagModel addedTag = tagRepository.save(tag);
            return addedTag;
        } catch (Exception ex) {
            throw new EntityException("An error occurred while tag was being added", ex);
        }
    }

    @Transactional
    public void deleteTag(long id) {
        //Comprueba que el elemento exista en la base de datos.
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Tag to be removed was not found");
        }
        try {
            tagRepository.deleteById(id);
        } catch (Exception e) {
            throw new EntityException("An error occurred while the tag was being removed: \n", e);
        }
    }

    @Transactional
    public TagModel updateTag(long id, TagModel tagUpdated) {
        Optional<TagModel> opTag = tagRepository.findById(id);
        if (opTag.isEmpty()) {
            throw new EntityNotFoundException("Error, tag was not found");
        }
        try {
            //Se actualizan los atributos del tag
            TagModel oldTag = opTag.get();
            oldTag.setNombre(tagUpdated.getNombre());
            return tagRepository.save(oldTag);
        } catch (Exception ex) {
            throw new EntityException("An error occurred while data was being updated: \n", ex);
        }
    }
}
