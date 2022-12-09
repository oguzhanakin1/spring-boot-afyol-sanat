package com.example.photographerApp.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MessageCreateRequest
{
    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String email;

    @NotBlank
    String message;
}
