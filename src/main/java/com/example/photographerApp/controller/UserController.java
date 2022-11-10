package com.example.photographerApp.controller;

import com.example.photographerApp.model.Authority;
import com.example.photographerApp.model.User;
import com.example.photographerApp.request.UserRequest;
import com.example.photographerApp.response.UserResponse;
import com.example.photographerApp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController
{
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService)
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
        User user = userService.findOneUserById(userId).orElseThrow(()->
                new UsernameNotFoundException("user not found with userId: " + userId));

        return ResponseEntity.ok(new UserResponse(user));
    }

    @PostMapping("create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request)
    {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId,
                                        @RequestBody UserRequest request)
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
