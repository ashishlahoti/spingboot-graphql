package com.example.api.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * CommenterInput
 */
@Data
public class CommenterInput {
    @NotNull(message = "Comment Id cannot be null")
    private Long commentId;
    @NotEmpty
    @Size(min = 4, max = 30, message = "Name must be between 4 and 30 characters")
    private String name;
    @Email(message = "You should specify valid email")
    private String email;
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    // @CustomDateConstraint
    private LocalDate dateOfBirth;
}
