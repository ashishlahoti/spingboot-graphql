package com.example.api.model;

import lombok.Value;

@Value
public class PostInput {

    Long userId;
    String title;
    String body;
}
