package com.example.photographerApp.service;

import com.example.photographerApp.model.Photo;
import com.example.photographerApp.repository.PhotoRepository;
import com.example.photographerApp.request.PhotoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService implements IPhotoService
{
    private PhotoRepository photoRepository;

    @Autowired
    public PhotoService(PhotoRepository photoRepository)
    {
        this.photoRepository = photoRepository;
    }

    @Override
    public Photo createPhoto(PhotoRequest request)
    {
        Photo photo = new Photo();
        photo.setPhotoUrl(request.getPhotoUrl());
        photo.setPhotoDescription(request.getPhotoDescription());
        photo.setPhotographerName(request.getPhotographerName());
        photo.setPhotoCreateTime(LocalDateTime.now());
        return photoRepository.save(photo);
    }

    @Override
    public Photo updatePhoto(Long photoId, PhotoRequest request)
    {
        Photo photoToEdit = photoRepository.findById(photoId).orElseThrow();
        photoToEdit.setPhotoDescription(request.getPhotoDescription());
        photoToEdit.setPhotographerName(request.getPhotographerName());
        photoToEdit.setPhotoUrl(request.getPhotoUrl());
        photoToEdit.setPhotoCreateTime(LocalDateTime.now());

        return photoRepository.save(photoToEdit);
    }

    @Override
    public void deleteOnePhotoById(Long photoId)
    {
        photoRepository.deleteById(photoId);
    }
    @Override
    public List<Photo> getAll()
    {
        return photoRepository.findAll();
    }

    @Override
    public Optional<Photo> findOnePhotoById(Long photoId)
    {
        return photoRepository.findById(photoId);
    }
}
