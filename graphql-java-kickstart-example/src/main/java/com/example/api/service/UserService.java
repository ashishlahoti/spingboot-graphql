package com.example.api.service;

import com.example.api.model.User;
import com.example.api.model.UserInput;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> getAllUsers();

    List<User> getAllUsersAfter(Long id);

    User getUserById(Long id);

    User createUser(UserInput userInput);

    User updateUser(Long id, UserInput userInput);

    Boolean deleteUser(Long id);
}
