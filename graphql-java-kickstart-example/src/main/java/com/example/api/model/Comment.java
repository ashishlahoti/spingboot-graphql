package com.example.api.model;

import lombok.Data;

/**
 * Comment Data class
 */
@Data
public class Comment {
    Long postId;
    Long id;
    String name;
    String email;
    String body;
    Commenter commenter;
}
