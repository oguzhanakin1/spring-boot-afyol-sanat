package com.example.photographerApp.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AnnouncementUpdateRequest
{
    @NotBlank
    String announcementBody;
}
