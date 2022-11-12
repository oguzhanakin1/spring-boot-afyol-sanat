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

    public PhotoResponse(Photo entity)
    {
        this.id = entity.getId();
        this.photoUrl = entity.getPhotoUrl();
        this.photoDescription = entity.getPhotoDescription();
        this.photographerName = entity.getPhotographerName();
        this.photoCreateTime = entity.getPhotoCreateTime();
    }
}
