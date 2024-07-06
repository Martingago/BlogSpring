package com.project.springBlog.services;

import com.project.springBlog.models.TagModel;
import com.project.springBlog.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}
