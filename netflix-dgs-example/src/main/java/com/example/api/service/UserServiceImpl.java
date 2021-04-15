package com.example.api.service;

import com.example.api.client.UserFeignClient;
import com.example.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserFeignClient userFeignClient;

    @Override
    public List<User> getAllUsers() {
        return userFeignClient.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userFeignClient.getUserById(id);
    }

}
