package com.example.api.model;

import lombok.Value;

/**
 * Commenter Data class
 */
@Value
public class Commenter {
    private Long id;
    private String name;
    private String email;
}
