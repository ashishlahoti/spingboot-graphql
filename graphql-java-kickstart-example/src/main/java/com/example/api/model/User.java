package com.example.api.model;

import lombok.Value;

@Value
public class User {

    Long id;
    String name;
    String username;
    String email;
    String phone;
    String website;
}
