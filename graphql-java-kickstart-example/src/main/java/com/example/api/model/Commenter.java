package com.example.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

/**
 * Commenter Data class
 */
@Data
@AllArgsConstructor
@Builder
public class Commenter {
    private Long id;
    private String name;
    private String email;
    private boolean publish;
}
