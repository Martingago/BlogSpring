package com.project.springBlog.controllers;

import com.project.springBlog.dtos.Publicacion;
import com.project.springBlog.dtos.PublicacionDetails;
import com.project.springBlog.dtos.ResponseDTO;
import com.project.springBlog.models.PostModel;
import com.project.springBlog.services.PostService;
import com.project.springBlog.services.PublicacionService;
import com.project.springBlog.utils.ValidationErrorUtil;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PublicacionController {
    @Autowired
    PublicacionService publicacionService;


    @GetMapping
    public ResponseEntity<ResponseDTO> getPublicaciones(){
        List<PublicacionDetails> listPublicacion =  publicacionService.getPublicacionesDetails();
        return new ResponseEntity<>(new ResponseDTO(true, "List of publicaciones founded", listPublicacion), HttpStatus.OK);
    }

    @GetMapping ("/sorted")
    public ResponseEntity<ResponseDTO> getPublicacionesSorted(@RequestParam(required = false) String field, @RequestParam(required = false) String order){
        List<PublicacionDetails> listPublicacion = publicacionService.getPublicacionesDetails(field, order);
        return new ResponseEntity<>(new ResponseDTO(true, "List of sorted publicaciones founded", listPublicacion), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPublicacion(@PathVariable("id") long id){
        PublicacionDetails publicacion =  publicacionService.getPublicacionDetails(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Post was successfully founded", publicacion), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addPublicacion(@Valid @RequestBody Publicacion publicacion, BindingResult result) {
        if (result.hasErrors()) {
            String err = ValidationErrorUtil.processValidationErrors(result);
            return new ResponseEntity<>(new ResponseDTO(false, err, null), HttpStatus.BAD_REQUEST);
        }
        PublicacionDetails pub = publicacionService.addPublicacion(publicacion);
        return new ResponseEntity<>(new ResponseDTO(true, "Post succesfully upload", pub), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> removePublicacion(@PathVariable("id") long id){
        publicacionService.deletePublicacion(id);
        return new ResponseEntity<>(new ResponseDTO(true, "Post was successfully deleted", null), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updatePublicacion(@PathVariable("id") long id, @Valid @RequestBody Publicacion publicacion, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(new ResponseDTO(false, result.toString(), null), HttpStatus.BAD_REQUEST);
        }
        PublicacionDetails pub = publicacionService.updatePublicacion(id, publicacion);
        return new ResponseEntity<>(new ResponseDTO(true, "Publicacion was successfully updated", pub), HttpStatus.OK);
    }

}
