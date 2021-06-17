package com.example.api.controller;

import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.model.UserInput;
import com.example.api.service.PostService;
import com.example.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    User createUser(@RequestBody UserInput userInput) {
        return userService.createUser(userInput);
    }

    @PutMapping("/{userId}")
    User updateUser(@PathVariable Long userId, @RequestBody UserInput userInput) {
        return userService.updateUser(userId, userInput);
    }

    @DeleteMapping("/{userId}")
    Boolean deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/{userId}/posts")
    List<Post> getPostsByUserId(@PathVariable Long userId) {
        return postService.getAllPostsByUserId(userId);
    }

}
