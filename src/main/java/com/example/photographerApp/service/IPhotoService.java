package com.example.photographerApp.service;

import com.example.photographerApp.model.Photo;
import com.example.photographerApp.request.PhotoRequest;

import java.util.List;
import java.util.Optional;

public interface IPhotoService {
    Photo createPhoto(PhotoRequest request);

    Photo updatePhoto(Long photoId, PhotoRequest request);

    void deleteOnePhotoById(Long photoId);

    List<Photo> getAll();

    Optional<Photo> findOnePhotoById(Long photoId);
}
