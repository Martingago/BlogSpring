package com.project.springBlog.controllers;

import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.services.TagService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping
    public ArrayList<TagModel> getTags() {
        return tagService.getTags();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getTagById(@PathVariable("id") long id) {
        TagModel tag = tagService.getTag(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag succesfully founded", tag), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addTag(@Valid @RequestBody TagModel tag, BindingResult result) {
        if ((result.hasErrors())) {
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        TagModel newTag = tagService.addTag(tag);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag successfully added", newTag), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag was succesfully removed", null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateTag(@Valid @PathVariable("id") long id, @Valid @RequestBody TagModel tag, BindingResult result) {
        if (result.hasErrors()) {
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        TagModel updatedTag = tagService.updateTag(id, tag);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag was succesfully updated", updatedTag), HttpStatus.OK);
    }

}





