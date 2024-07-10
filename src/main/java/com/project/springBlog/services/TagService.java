package com.project.springBlog.services;

import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<TagModel> tagModel = tagRepository.findById(id);
        return tagModel.orElse(null);
    }

    public TagModel addTag(TagModel tag){
        return tagRepository.save(tag);
    }

    @Transactional
    public boolean deleteTag(long id) {
        //Comprueba que el elemento exista en la base de datos.
        if(!tagRepository.existsById(id)){
            return false;
        }
        try {
            tagRepository.deleteById(id);
            return true;
        }  catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public ResponseEntity<String> updateTag(long id, TagModel tagUpdated){
        Optional<TagModel> opTag = tagRepository.findById(id);
        if(!opTag.isPresent()){
            return new ResponseEntity<>("Tag not found" , HttpStatus.NOT_FOUND);
        }
        try {
            //Se actualizan los atributos del tag
            TagModel oldTag = opTag.get();
            oldTag.setNombre(tagUpdated.getNombre());
            tagRepository.save(oldTag);
            return new ResponseEntity<>("Tag succesfully updated", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("An error ocurred while tag was updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
