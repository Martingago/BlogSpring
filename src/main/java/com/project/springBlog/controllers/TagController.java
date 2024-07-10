package com.project.springBlog.controllers;

import com.project.springBlog.models.TagModel;
import com.project.springBlog.services.TagService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/{id}")
    public TagModel getTagById(@PathVariable("id")  long id){
        return this.tagService.getTag(id);
    }

    @PostMapping
    public TagModel addTag(@RequestBody TagModel tag){
        return this.tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable("id") long id){
        boolean isRemoved = tagService.deleteTag(id);
        if (!isRemoved) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tag not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Tag deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTag(@PathVariable("id") long id, @Valid @RequestBody TagModel tag, BindingResult result) {
        if (result.hasErrors()) { //Valida la integridad del objeto recibido. Si result tiene errores devuelve los errores existentes
            return ValidationErrorUtil.processValidationErrors(result);
        }
        return tagService.updateTag(id, tag);
    }

    }



