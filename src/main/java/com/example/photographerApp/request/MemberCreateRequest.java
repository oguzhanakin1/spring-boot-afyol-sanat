package com.example.photographerApp.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberCreateRequest
{
    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotNull
    String memberRole;
}
