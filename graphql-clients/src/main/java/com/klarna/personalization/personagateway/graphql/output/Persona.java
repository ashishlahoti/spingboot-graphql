package com.klarna.personalization.personagateway.graphql.output;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Persona {
    private UUID id;
    private Instant createdAt;
    private String shortId;
    private String krn;
    private String contactEmail;
}
