package com.example.photographerApp.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EventCreateRequest
{
    @NotBlank
    String photo;

    @NotBlank
    String details;
}
