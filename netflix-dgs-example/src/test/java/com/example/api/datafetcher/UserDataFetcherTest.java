package com.example.api.datafetcher;

import com.example.api.model.User;
import com.example.api.service.PostService;
import com.example.api.service.UserService;
import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest(classes = {DgsAutoConfiguration.class, UserDataFetcher.class})
public class UserDataFetcherTest {

    @Autowired
    DgsQueryExecutor dgsQueryExecutor;

    @MockBean
    UserService userService;

    @MockBean
    PostService postService;

    @Test
    void users() {
        Mockito.when(userService.getAllUsers()).thenAnswer(invocation -> List.of(new User(1L, "Jon Doe")));
        List<String> users = dgsQueryExecutor.executeAndExtractJsonPath(
                "{ users {id}}",
                "data.users[*].id");

        Assertions.assertTrue(users.contains("1"));
    }
}
