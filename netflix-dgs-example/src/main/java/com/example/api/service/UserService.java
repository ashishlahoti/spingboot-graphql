package com.example.api.service;

import com.example.api.model.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public User getUserById(Long id);
}
