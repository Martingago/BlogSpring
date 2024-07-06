package com.project.springBlog.controllers;

import com.project.springBlog.models.TagModel;
import com.project.springBlog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping
    public ArrayList<TagModel> getTags(){
        return tagService.getTags();
    }

    @PostMapping
    public TagModel setTag(@RequestBody TagModel tag){
        return this.tagService.addTag(tag);
    }
}
