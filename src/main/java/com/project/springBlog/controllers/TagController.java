package com.project.springBlog.controllers;

import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.dtos.TagDTO;
import com.project.springBlog.mapper.TagMapper;
import com.project.springBlog.models.TagModel;
import com.project.springBlog.services.TagService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("public/tags")
    public ResponseEntity<ResponseDTO> getTags(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "12") int size
    ) {
        Page<TagDTO> tags =  tagService.getTagsPaginated(null, null, page, size);
        return new ResponseEntity<>(new ResponseDTO(true, "List of tags", tags), HttpStatus.OK);
    }

    @GetMapping("public/tags/{id}")
    public ResponseEntity<ResponseDTO> getTagById(@PathVariable("id") long id) {
        TagModel tag = tagService.getTag(id);
        TagDTO tagDTO = TagMapper.toDTO(tag);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag succesfully founded", tagDTO), HttpStatus.OK);
    }

    @PostMapping("/admin/tags")
    public ResponseEntity<ResponseDTO> addTag(@Valid @RequestBody TagModel tag, BindingResult result) {
        if ((result.hasErrors())) {
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        TagModel newTag = tagService.addTag(tag);
        //Se devuelve el DTO de la tag como respuesta
        TagDTO tagDTO = TagMapper.toSimpleDTO(newTag);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag successfully added", tagDTO), HttpStatus.OK);
    }

    @DeleteMapping("admin/tags/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable("id") long id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag was succesfully removed", null), HttpStatus.OK);
    }

    @PutMapping("admin/tags/{id}")
    public ResponseEntity<ResponseDTO> updateTag(@Valid @PathVariable("id") long id, @Valid @RequestBody TagModel tag, BindingResult result) {
        if (result.hasErrors()) {
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        TagModel updatedTag = tagService.updateTag(id, tag);
        //Se devuelve el DTO de la tag como respuesta
        TagDTO tagDTO = TagMapper.toSimpleDTO(updatedTag);
        return new ResponseEntity<>(new ResponseDTO(true, "Tag was succesfully updated", tagDTO), HttpStatus.OK);
    }

}





