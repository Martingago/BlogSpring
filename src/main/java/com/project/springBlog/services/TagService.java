package com.project.springBlog.services;

import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if(tagModel.isPresent()){
            return tagModel.get();
        }else{
            throw new NoSuchElementException("No se ha encontrado un tag con uid " + id);
        }
    }
}
