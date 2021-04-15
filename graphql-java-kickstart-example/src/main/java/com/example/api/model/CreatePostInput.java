package com.example.api.model;

public class CreatePostInput {

    Long userId;
    String title;
    String body;

    public CreatePostInput(Long userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
