package com.example.api.service;

import com.example.api.client.UserFeignClient;
import com.example.api.model.User;
import com.example.api.model.UserInput;
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

    @Override
    public User createUser(UserInput userInput) {
        return userFeignClient.createUser(userInput);
    }

    @Override
    public User updateUser(UserInput userInput) {
        return userFeignClient.updateUser(userInput);
    }

    @Override
    public Boolean deleteUser(Long id) {
        return userFeignClient.deleteUser(id);
    }

}
