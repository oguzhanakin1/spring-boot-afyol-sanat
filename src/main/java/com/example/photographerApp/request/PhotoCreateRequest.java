package com.example.photographerApp.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PhotoCreateRequest
{
    @NotBlank
    String photoUrl;

    @NotNull
    String photoDescription;

    @NotNull
    String photographerName;
}
