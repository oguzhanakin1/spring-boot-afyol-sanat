package com.example.photographerApp.controller;

import com.example.photographerApp.model.User;
import com.example.photographerApp.request.UserCreateRequest;
import com.example.photographerApp.request.UserUpdateRequest;
import com.example.photographerApp.response.UserResponse;
import com.example.photographerApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> findAll()
    {
        return ResponseEntity.ok(userService.getAll().stream().map(user -> new UserResponse(user)).collect(Collectors.toList()));
    }

    @GetMapping("get/{userId}")
    public ResponseEntity<?> findOneUserByUserId(@PathVariable Long userId)
    {
        User user = userService.findOneUserById(userId);

        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateRequest request)
    {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @RequestBody UserUpdateRequest request)
    {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<?> deleteOneUserByUserId(@PathVariable Long userId)
    {

        userService.deleteOneUserById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
