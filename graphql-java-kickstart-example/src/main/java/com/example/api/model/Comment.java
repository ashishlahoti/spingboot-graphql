package com.example.api.model;

import lombok.Value;

/**
 * Comment Data class
 */
@Value
public class Comment {
    Long postId;
    Long id;
    String name;
    String email;
    String body;
    Commenter commenter;
}
