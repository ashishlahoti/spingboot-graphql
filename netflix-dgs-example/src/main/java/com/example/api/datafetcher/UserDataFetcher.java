package com.example.api.datafetcher;

import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.model.UserInput;
import com.example.api.service.PostService;
import com.example.api.service.UserService;
import com.netflix.graphql.dgs.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
public class UserDataFetcher {

    private final UserService userService;
    private final PostService postService;

    @DgsQuery
    public List<User> users() {
        return userService.getAllUsers();
    }

    @DgsQuery
    public User userById(String id) {
        return userService.getUserById(Long.parseLong(id));
    }

    @DgsMutation
    public User createUser(@InputArgument("input") UserInput userInput) { return userService.createUser(userInput); }

    @DgsMutation
    public User updateUser(@InputArgument("input") UserInput userInput) { return userService.updateUser(userInput); }

    @DgsMutation
    public Boolean deleteUser(String id) { return userService.deleteUser(Long.parseLong(id)); }

    @DgsData(parentType = "User")
    public List<Post> posts(DgsDataFetchingEnvironment dfe){
        User user = dfe.getSource();
        return postService.getAllPostsByUserId(user.getId());
    }
}
