package com.example.photographerApp.request;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PhotoUpdateRequest
{
    @NotBlank
    String photoUrl;

    @NotNull
    String photoDescription;

    @NotNull
    String photographerName;
}
