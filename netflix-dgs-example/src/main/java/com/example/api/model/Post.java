package com.example.api.model;

public class Post {

    Long id;
    Long userId;
    String title;
    String body;

    public Post(Long id, Long userId, String title, String body) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Long getId() {
        return id;
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
