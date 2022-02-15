package com.example.api.datafetcher;

import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.service.PostService;
import com.example.api.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = {DgsAutoConfiguration.class, UserDataFetcher.class})
public class UserDataFetcherTest {

    private static final String ID_TOKEN = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private HttpHeaders headers = new HttpHeaders();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String GRAPHQL_QUERY_REQUEST_PATH = "graphql/user/request/%s.graphqls";
    private static final String GRAPHQL_QUERY_RESPONSE_PATH = "graphql/user/response/%s.json";

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    UserService userService;

    @MockBean
    PostService postService;

    @BeforeEach
    private void setup() {
        headers.set("ID-Token", ID_TOKEN);
        when(userService.getAllUsers()).thenReturn(
                List.of(
                        new User(1L, "Jon Doe"),
                        new User(2L, "Justin Case")
                )
        );
        when(userService.getUserById(anyLong())).thenReturn(new User(1L, "Leanne Graham"));
        when(postService.getAllPostsByUserId(anyLong())).thenReturn(
                List.of(
                        new Post(1L, 1L, "post_title", "post_body")
                )
        );
    }

    @Test
    @DisplayName("Return valid list of users from graphql query")
    void users_ReturnValidListOfUsers() throws IOException {
        String graphQLQuery = read(String.format(GRAPHQL_QUERY_REQUEST_PATH, "users"));
        List<String> users = dgsQueryExecutor.executeAndExtractJsonPath(graphQLQuery, "data.users[*].id");
        Assertions.assertTrue(users.contains("1"));
        Assertions.assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Return valid user by passing id as variable and http-headers in graphql query")
    void userById_WithVariablesAndHeaders_ReturnValidUser() throws IOException {
        String graphQLQuery = read(String.format(GRAPHQL_QUERY_REQUEST_PATH, "userById"));
        Object result = dgsQueryExecutor.execute(graphQLQuery, Map.of("userId", "1"), null, headers).getData();
        String actualJsonResponse = objectMapper.writeValueAsString(result);
        String expectedJsonResponse = objectMapper.readValue(read(String.format(GRAPHQL_QUERY_RESPONSE_PATH, "userById")),
                JsonNode.class).toString();
        Assertions.assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    private String read(String location) throws IOException {
        return IOUtils.toString(new ClassPathResource(location).getInputStream(), String.valueOf(StandardCharsets.UTF_8));
    }
}
