package com.example.api.webclient;

import com.example.api.model.User;
import com.klarna.personalization.personagateway.graphql.output.Persona;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLResponse;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * https://github.com/graphql-java-kickstart/graphql-spring-webclient
 * https://jarcasting.in/artifacts/com.graphql-java-kickstart/graphql-webclient/
 */

@Component
@Slf4j
public class MyGraphQLWebClient {

    private final GraphQLWebClient graphQLWebClient;

    MyGraphQLWebClient(GraphQLWebClient graphQLWebClient) {
        this.graphQLWebClient = graphQLWebClient;
    }

    public String getUserById(Long id) {
        log.info("getUserById with id: {}", id);

        // User user = graphQLWebClient.post("graphql/query2.graphql", Map.of("userId", 1L), User.class).block();
        // log.info("getUserById user: {}", user);


        return getUserById2(id);
    }

    public String getUserById2(Long id) {
        log.info("getUserById2 with id: {}", id);
        String auth = "admin:cy74LPsDnVPX1oMNjhoCl5MTLjf4g5Md";
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);
        log.info("getUserById2 authHeader: {}", authHeader);

        GraphQLRequest request = GraphQLRequest.builder()
                //.query("query GET_USER_BY_ID {userById(id: 1) {id name username email phone}}")
                .resource("graphql/persona.graphql")
                .header("Authorization", authHeader)
                .operationName("PERSONA")
                .variables(Map.of("email", "jose-ignacio.martin@klarna.com"))
                .build();
        log.info("getUserById2 request: {}", request);
        GraphQLResponse response = graphQLWebClient.post(request).block();
        log.info("getUserById2 response: {}", response);
        Persona persona = response.getFirst(Persona.class);
        log.info("getUserById2 persona: {}", persona);

        return response.toString();
    }
}
