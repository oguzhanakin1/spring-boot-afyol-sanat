package com.example.photographerApp.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateRequest
{
    @NotBlank
    String email;

    @NotBlank
    String password;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    Long authorityId;
}
