package com.project.springBlog.dtos;

import com.project.springBlog.models.TagModel;

public class TagResponseDTO {
    private final boolean success;
    private final String statusMessage;
    private final TagModel tagResponse;

    public TagResponseDTO(boolean success, String statusMessage, TagModel tagResponse) {
        this.success = success;
        this.statusMessage = statusMessage;
        this.tagResponse = tagResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public TagModel getTagResponse() {
        return tagResponse;
    }
}
