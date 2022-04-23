package com.example.api.model;

import lombok.Value;

/**
 * @Value Generates a lot of code which fits with a class that is a representation of an immutable entity.
 */
@Value
public class PostInput {
    Long userId;
    String title;
    String body;
}
