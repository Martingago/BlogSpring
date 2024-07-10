package com.project.springBlog.services;

import com.project.springBlog.dtos.TagResponseDTO;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    public TagModel getTag(long id){
        try {
            Optional<TagModel> tagModel = tagRepository.findById(id);
            return tagModel.orElseThrow(() -> new EntityNotFoundException("Tag was not found"));
        }catch (Exception e){
            throw new RuntimeException("Exeption during getting tag " + e);
    }
    }

    public ResponseEntity<TagResponseDTO> addTag(TagModel tag){
        TagModel addedTag = tagRepository.save(tag);
        if(addedTag == null){
            return new ResponseEntity<>(new TagResponseDTO(false, "Error adding tag", addedTag), HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            return new ResponseEntity<>(new TagResponseDTO(true,"Tag successfully added", addedTag), HttpStatus.OK);
        }
    }

    @Transactional
    public ResponseEntity<TagResponseDTO> deleteTag(long id) {
        //Comprueba que el elemento exista en la base de datos.
        if(!tagRepository.existsById(id)){
            return new ResponseEntity<>(new TagResponseDTO(false, "Tag to be removed was not found", null), HttpStatus.NOT_FOUND);
        }
        try {
            tagRepository.deleteById(id);
            return new ResponseEntity<>(new TagResponseDTO(true, "Tag was successfully removed", null), HttpStatus.OK);
        }  catch (Exception e) {
            return new ResponseEntity<>(new TagResponseDTO(false, "An error occurred while the tag was being removed", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<TagResponseDTO> updateTag(long id, TagModel tagUpdated){
        Optional<TagModel> opTag = tagRepository.findById(id);
        if(opTag.isEmpty()){
            return new ResponseEntity<>(new TagResponseDTO(false, "Tag was not founded", null), HttpStatus.NOT_FOUND);
        }
        try {
            //Se actualizan los atributos del tag
            TagModel oldTag = opTag.get();
            oldTag.setNombre(tagUpdated.getNombre());
            tagRepository.save(oldTag);
            return new ResponseEntity<>(new TagResponseDTO(true, "Tag successfully updated", oldTag), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new TagResponseDTO(false, "An error occurred while the tag was being updated", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
