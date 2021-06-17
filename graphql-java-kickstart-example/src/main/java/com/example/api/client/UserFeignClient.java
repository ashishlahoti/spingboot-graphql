package com.example.api.client;

import com.example.api.model.User;
import com.example.api.model.UserInput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "userFeignClient", url = "https://jsonplaceholder.typicode.com")
public interface UserFeignClient {
    @GetMapping("/users")
    List<User> getAllUsers();

    @GetMapping("/users/{userId}")
    User getUserById(@PathVariable Long userId);

    @PostMapping("/users")
    User createUser(UserInput userInput);

    @PutMapping("/users/{userId}")
    User updateUser(@PathVariable Long userId, UserInput userInput);

    @DeleteMapping("/users/{userId}")
    void deleteUser(@PathVariable Long userId);
}
