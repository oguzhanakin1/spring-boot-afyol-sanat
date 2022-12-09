package com.example.photographerApp.service;

import com.example.photographerApp.exception.PhotoNotFoundException;
import com.example.photographerApp.model.Photo;
import com.example.photographerApp.repository.PhotoRepository;
import com.example.photographerApp.request.PhotoCreateRequest;
import com.example.photographerApp.request.PhotoUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotoService
{
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository)
    {
        this.photoRepository = photoRepository;
    }

    public Photo createPhoto(PhotoCreateRequest request)
    {
        Photo photo = new Photo();
        photo.setPhotoUrl(request.getPhotoUrl());
        photo.setPhotoDescription(request.getPhotoDescription());
        photo.setPhotographerName(request.getPhotographerName());
        photo.setPhotoCreateTime(LocalDateTime.now());
        return photoRepository.save(photo);
    }

    public Photo updatePhoto(Long photoId, PhotoUpdateRequest request)
    {
        Photo photoToEdit = photoRepository.findById(photoId)
                .orElseThrow(() ->
                        new PhotoNotFoundException
                                ("Photo not found with id:" + photoId));
        photoToEdit.setPhotoDescription(request.getPhotoDescription());
        photoToEdit.setPhotographerName(request.getPhotographerName());
        photoToEdit.setPhotoUrl(request.getPhotoUrl());

        return photoRepository.save(photoToEdit);
    }

    public void deleteOnePhotoById(Long photoId)
    {
        photoRepository.deleteById(photoId);
    }

    public List<Photo> getAll()
    {
        return photoRepository.findAll();
    }

    public Photo findOnePhotoById(Long photoId)
    {
        return photoRepository.findById(photoId)
                .orElseThrow(() ->
                        new PhotoNotFoundException
                                ("Photo not found with id:" + photoId));
    }
}
