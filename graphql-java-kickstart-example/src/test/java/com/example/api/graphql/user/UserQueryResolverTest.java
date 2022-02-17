package com.example.api.graphql.user;

import com.example.api.KickStartApiApplication;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = KickStartApiApplication.class)
class UserQueryResolverTest {

    private static final String GRAPHQL_QUERY_REQUEST_PATH = "graphql/user/request/%s.graphqls";
    private static final String GRAPHQL_QUERY_RESPONSE_PATH = "graphql/user/response/%s.json";

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;  // Helper class to test GraphQL queries and mutations.

    @Test
    void user_are_returned() throws IOException {
        String testName = "user";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(String.format(GRAPHQL_QUERY_REQUEST_PATH, testName));
        String response = read(String.format(GRAPHQL_QUERY_RESPONSE_PATH, testName));

        assertEquals(HttpStatus.OK, graphQLResponse.getStatusCode());
        assertEquals(response, graphQLResponse.getRawResponse().getBody());
    }

    private String read(String location) throws IOException {
        return IOUtils.toString(new ClassPathResource(location).getInputStream(), StandardCharsets.UTF_8);
    }
}
