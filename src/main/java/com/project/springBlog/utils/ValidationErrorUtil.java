package com.project.springBlog.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidationErrorUtil {

    /**
     * Funcion que recibe un objeto BindingResult con los errores de una solicitud y devuelve un estado de errores
     * @param result
     * @return ResponseEntity con los errores encontrados
     */
    public static String processValidationErrors(BindingResult result){
        StringBuilder errorMesagge = new StringBuilder();
        result.getAllErrors().forEach(error -> errorMesagge.append(error.getDefaultMessage()).append(System.lineSeparator()));
        return errorMesagge.toString();
    }
}
