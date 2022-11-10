package com.example.photographerApp.request;


import lombok.Data;

@Data
public class PhotoRequest
{
    String photoUrl;

    String photoDescription;

    String photographerName;
}
