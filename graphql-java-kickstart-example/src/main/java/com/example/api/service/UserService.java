package com.example.api.service;

import com.example.api.model.User;
import com.example.api.model.UserInput;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public User createUser(UserInput userInput);

    public User updateUser(UserInput userInput);

    public Boolean deleteUser(Long id);
}
