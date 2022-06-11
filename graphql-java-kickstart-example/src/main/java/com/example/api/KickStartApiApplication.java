package com.example.api;

import com.example.api.config.graphQL.directives.AuthorisationDirective;
import com.example.api.config.graphQL.directives.ValidateUserInputDirective;
import com.example.api.config.graphQL.directives.UppercaseDirective;
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


}
