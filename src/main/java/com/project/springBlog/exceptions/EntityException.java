package com.project.springBlog.exceptions;

public class EntityException extends  RuntimeException{

    public EntityException(String message, Throwable cause){
        super(message, cause);
    }
}
