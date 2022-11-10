package com.example.photographerApp.request;

import lombok.Data;

@Data
public class UserRequest
{
    Long id;
    String email;
    String password;
    String firstName;
    String lastName;
    Long authorityId;
}
