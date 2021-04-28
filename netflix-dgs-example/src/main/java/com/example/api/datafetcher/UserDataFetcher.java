package com.example.api.datafetcher;

import com.example.api.context.ContextAwarePoolExecutor;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.model.UserInput;
import com.example.api.service.UserService;
import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher {

    private final UserService userService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5, new ContextAwarePoolExecutor());

    @DgsQuery
    public List<User> users() {
        return userService.getAllUsers();
    }

    @DgsQuery
    public User userById(String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @DgsMutation
    public User createUser(@InputArgument("input") UserInput userInput) {
        return userService.createUser(userInput);
    }

    @DgsMutation
    public User updateUser(@InputArgument("input") UserInput userInput) {
        return userService.updateUser(userInput);
    }

    @DgsMutation
    public Boolean deleteUser(String id) {
        return userService.deleteUser(Long.parseLong(id));
    }

    @DgsData(parentType = "Post", field = "user")
    public CompletableFuture<User> userByPost(DgsDataFetchingEnvironment dfe) {
        Post post = dfe.getSource();
        return CompletableFuture.supplyAsync(() -> userService.getUserById(post.getUserId()), executorService);
    }
}
