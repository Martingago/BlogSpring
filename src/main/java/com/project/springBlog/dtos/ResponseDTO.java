package com.project.springBlog.dtos;

import com.project.springBlog.models.TagModel;

public class ResponseDTO {
    private final boolean success;
    private final String statusMessage;
    private final Object objectResponse;

    public ResponseDTO(boolean success, String statusMessage, Object objectResponse) {
        this.success = success;
        this.statusMessage = statusMessage;
        this.objectResponse = objectResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Object getObjectResponse() {
        return objectResponse;
    }
}
