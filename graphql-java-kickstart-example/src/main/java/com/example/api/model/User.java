package com.example.api.model;

import lombok.Value;

/**
 * @Value Generates a lot of code which fits with a class that is a representation of an immutable entity.
 */
@Value
public class User {
    Long id;
    String name;
    String username;
    String email;
    String phone;
    String website;
    Address address;
}
