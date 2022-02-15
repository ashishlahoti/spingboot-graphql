package com.example.api.model;

import lombok.Value;

/**
 * @Value Generates a lot of code which fits with a class that is a representation of an immutable entity.
 */
@Value
public class Comment {

    Long postId;
    Long id;
    String name;
    String email;
    String body;
}
