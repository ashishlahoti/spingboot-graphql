package com.example.api.model;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class Company {
    String name;
    String catchPhrase;
    String bs;
}
