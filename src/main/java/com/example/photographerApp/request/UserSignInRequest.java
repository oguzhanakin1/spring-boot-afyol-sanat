package com.example.photographerApp.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserSignInRequest
{
    @NotBlank
    String email;

    @NotBlank
    String password;
}
