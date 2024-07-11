package com.project.springBlog.exceptions;

import com.project.springBlog.dtos.ResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Gestiona el error de una entidad no encontrada
     * @param exception error recibido
     * @return responseEntity
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDTO> hanldleEntityNotFound(EntityNotFoundException exception) {
        return new ResponseEntity<>(createResponseDTO(false,exception.getMessage(), null), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityException.class)
    public ResponseEntity<ResponseDTO> handleEntityDelete(EntityException exception){
        return new ResponseEntity<>(createResponseDTO(false, exception.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleEntityArguments(MethodArgumentNotValidException exception){
        return new ResponseEntity<>(createResponseDTO(false, exception.getMessage(), null), HttpStatus.METHOD_NOT_ALLOWED);
    }


    /**
     * Genera un responseDTO adaptado a la entidad recibida
     * @param succes
     * @param message
     * @param object
     */
    private ResponseDTO createResponseDTO(boolean succes, String message, Object object) {
        return new ResponseDTO(succes, message, object);
    }
}
