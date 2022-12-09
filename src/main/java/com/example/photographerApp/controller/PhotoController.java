package com.example.photographerApp.controller;

import com.example.photographerApp.model.Photo;
import com.example.photographerApp.request.PhotoCreateRequest;
import com.example.photographerApp.request.PhotoUpdateRequest;
import com.example.photographerApp.response.PhotoResponse;
import com.example.photographerApp.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/photos")
public class PhotoController
{
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService)
    {
        this.photoService = photoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PhotoResponse>> findAll()
    {
        return ResponseEntity.ok(photoService.getAll().stream().map(
                photo -> new PhotoResponse(photo)).collect(Collectors.toList()));
    }

    @GetMapping("get/{photoId}")
    public ResponseEntity<?> findOnePhotoById(@PathVariable Long photoId)
    {
        Photo photo = photoService.findOnePhotoById(photoId);
        return ResponseEntity.ok(new PhotoResponse(photo));
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> createPhoto(@RequestBody PhotoCreateRequest request)
    {
        return new ResponseEntity<>(photoService.createPhoto(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{photoId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> updatePhoto(@PathVariable Long photoId,
                                         @RequestBody PhotoUpdateRequest request)
    {
        return ResponseEntity.ok(photoService.updatePhoto(photoId, request));
    }

    @DeleteMapping("delete/{photoId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> deleteOnePhotoByPhotoId(@PathVariable Long photoId)
    {

        photoService.deleteOnePhotoById(photoId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
