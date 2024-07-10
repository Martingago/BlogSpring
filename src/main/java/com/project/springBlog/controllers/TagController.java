package com.project.springBlog.controllers;

import com.project.springBlog.dtos.TagResponseDTO;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.services.TagService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
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
    public ResponseEntity<TagResponseDTO> getTagById(@PathVariable("id")  long id){
        try {
            TagModel tag = tagService.getTag(id);
            return new ResponseEntity<>(new TagResponseDTO(true, "Tag succesfully founded", tag), HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(new TagResponseDTO(false,"Tag was not founded", null), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addTag(@Valid @RequestBody TagModel tag, BindingResult result){
        if((result.hasErrors())){
            return ValidationErrorUtil.processValidationErrors(result);
        }
        return tagService.addTag(tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id){
        return tagService.deleteTag(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(@PathVariable("id") long id, @Valid @RequestBody TagModel tag, BindingResult result) {
        if (result.hasErrors()) { //Valida la integridad del objeto recibido. Si result tiene errores devuelve los errores existentes
            return ValidationErrorUtil.processValidationErrors(result);
        }
        return tagService.updateTag(id, tag);
    }

    }



