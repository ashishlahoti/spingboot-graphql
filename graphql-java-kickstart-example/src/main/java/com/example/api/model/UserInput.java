package com.example.api.model;

import lombok.Value;

import javax.validation.constraints.NotBlank;

/**
 * @Value Generates a lot of code which fits with a class that is a representation of an immutable entity.
 */
@Value
public class UserInput {
    @NotBlank
    String name;
    @NotBlank
    String username;
    String email;
    String phone;
    String website;
}
