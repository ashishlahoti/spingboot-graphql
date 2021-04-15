package com.example.api.client;

import com.example.api.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "userFeignClient",
        url = "https://jsonplaceholder.typicode.com")
public interface UserFeignClient {
    @GetMapping("/users")
    List<User> getAllUsers();

    @GetMapping("/users/{userId}")
    User getUserById(@PathVariable Long userId);
}
