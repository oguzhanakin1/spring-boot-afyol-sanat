package com.example.photographerApp.response;

import com.example.photographerApp.model.Photo;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PhotoResponse
{
    Long id;

    String photoUrl;

    String photoDescription;

    String photographerName;

    LocalDateTime photoCreateTime;

    public PhotoResponse(Photo photo)
    {
        this.id = photo.getId();
        this.photoUrl = photo.getPhotoUrl();
        this.photoDescription = photo.getPhotoDescription();
        this.photographerName = photo.getPhotographerName();
        this.photoCreateTime = photo.getPhotoCreateTime();
    }
}
