package com.example.photographerApp.service;

import com.example.photographerApp.exception.PhotoNotFoundException;
import com.example.photographerApp.model.Photo;
import com.example.photographerApp.repository.PhotoRepository;
import com.example.photographerApp.request.PhotoCreateRequest;
import com.example.photographerApp.request.PhotoUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class PhotoServiceTest
{
    private PhotoService photoService;

    private PhotoRepository photoRepository;

    @BeforeEach
    public void setUp()
    {
        photoRepository = mock(PhotoRepository.class);
        photoService = new PhotoService(photoRepository);
    }

    @Test
    void findOnePhotoById_whenPhotoIdExists_shouldReturnPhoto()
    {
        Photo photo = new Photo
                (1L,"p_url","p_description","p_name",null);

        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));

        Photo result = photoService.findOnePhotoById(1L);

        assertEquals(result,photo);
    }

    @Test
    void findOnePhotoById_whenPhotoIdDoesNotExists_shouldThrowPhotoNotFoundException()
    {
        when(photoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PhotoNotFoundException.class,
                () -> photoService.findOnePhotoById(1L));
    }

    @Test
    void findAllPhoto_whenAnyAmountOfPhotoExists_shouldReturnPhotoResponseList()
    {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo());

        when(photoRepository.findAll()).thenReturn(photos);

        List<Photo> expected = photoService.getAll();

        assertEquals(expected,photos);

        verify(photoRepository).findAll();
    }

    @Test
    void createPhoto_whenCreatePhoto_shouldReturnPhoto()
    {
        Photo photo = new Photo(
                1L,"p_url","p_description","p_name",null);


        PhotoCreateRequest request = new PhotoCreateRequest();
        request.setPhotoUrl("new p_url");
        request.setPhotoDescription("new p_description");
        request.setPhotographerName("new p_name");

        when(photoRepository.save(ArgumentMatchers.any(Photo.class))).thenReturn(photo);

        Photo createdPhoto = photoService.createPhoto(request);

        assertEquals(createdPhoto,photo);
    }

    @Test
    void updatePhoto_whenPhotoExists_shouldReturnPhoto()
    {
        Photo photo = new Photo(
                1L,"p_url","p_description","p_name",null);

        Photo editedPhoto = new Photo(
                1L,"new p_url", "new p_description",
                "new p_name",null);

        PhotoUpdateRequest request = new PhotoUpdateRequest();

        request.setPhotoUrl("new p_url");
        request.setPhotoDescription("new p_description");
        request.setPhotographerName("new p_name");

        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));
        when(photoRepository.save(ArgumentMatchers.any(Photo.class))).thenReturn(photo);

        Photo updatedPhoto = photoService.updatePhoto(1L, request);

        verify(photoRepository).findById(1L);
        verify(photoRepository).save(editedPhoto);

        assertEquals(updatedPhoto,editedPhoto);
    }

    @Test
    void updatePhoto_whenPhotoDoesNotExists_shouldThrowPhotoNotFoundException()
    {
        when(photoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PhotoNotFoundException.class,
                () -> photoService.findOnePhotoById(1L));
    }

    @Test
    void deletePhoto_whenPhotoExists_shouldDeleteUser()
    {
        Photo photo = new Photo
                (1L,"p_url","p_description","p_name",null);

        when(photoRepository.findById(1L)).thenReturn(Optional.of(photo));

        photoService.deleteOnePhotoById(1L);

        verify(photoRepository).deleteById(1L);
    }

    @Test
    void deletePhoto_whenPhotoExists_shouldThrowPhotoNotFoundException()
    {
        when(photoRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PhotoNotFoundException.class,
                () -> photoService.findOnePhotoById(1L));
    }
}