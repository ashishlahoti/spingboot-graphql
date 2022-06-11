package com.example.api.config.graphQL.directives;

import graphql.kickstart.autoconfigure.tools.SchemaDirective;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Directives configuration for security management, validations and other custom testing samples
 */
@Configuration
public class DirectivesConfig {
    @Bean
    public SchemaDirective uppercaseDirective() {
        return new SchemaDirective("uppercase", new UppercaseDirective());
    }

    @Bean
    public SchemaDirective authorisationDirective() {
        return new SchemaDirective("auth", new AuthorisationDirective());
    }

    @Bean
    public SchemaDirective validateUserInputDirective() {
        return new SchemaDirective("validateUserInput", new ValidateUserInputDirective());
    }
}
