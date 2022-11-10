package com.example.photographerApp.service;

import com.example.photographerApp.model.User;
import com.example.photographerApp.request.UserRequest;

import java.util.List;
import java.util.Optional;

public interface IUserService
{
    User createUser(UserRequest request);

    User updateUser(Long userId, UserRequest request);

    void deleteOneUserById(Long userId);

    List<User> getAll();

    Optional<User> findOneUserById(Long userId);

    User findOneUserByEmail(String email);
}
