package com.example.api;

import com.example.api.config.graphQL.AuthorisationDirective;
import com.example.api.config.graphQL.ValidateUserInputDirective;
import com.example.api.config.graphQL.UppercaseDirective;
import graphql.kickstart.autoconfigure.tools.SchemaDirective;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class KickStartApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(KickStartApiApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

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
