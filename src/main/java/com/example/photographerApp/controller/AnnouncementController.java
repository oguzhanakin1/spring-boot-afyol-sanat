package com.example.photographerApp.controller;
import com.example.photographerApp.model.Announcement;
import com.example.photographerApp.request.AnnouncementCreateRequest;
import com.example.photographerApp.request.AnnouncementUpdateRequest;
import com.example.photographerApp.response.AnnouncementResponse;
import com.example.photographerApp.service.AnnouncementService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/announcements")
public class AnnouncementController
{
    private final AnnouncementService announcementService;


    public AnnouncementController(AnnouncementService announcementService)
    {
        this.announcementService = announcementService;
    }

    @GetMapping("all")
    public ResponseEntity<List<AnnouncementResponse>> findAll()
    {
        return ResponseEntity.ok(announcementService.getAll().stream().map(
                announcement -> new AnnouncementResponse(announcement)).collect(Collectors.toList()));
    }

    @GetMapping("get/{announcementId}")
    public ResponseEntity<?> findOneAnnouncementById(@PathVariable Long announcementId)
    {
        Announcement announcement = announcementService.findOneAnnouncementById(announcementId);
        return ResponseEntity.ok(new AnnouncementResponse(announcement));
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> createAnnouncement(@Valid @RequestBody AnnouncementCreateRequest request)
    {
        return new ResponseEntity<>(announcementService.createAnnouncement(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{announcementId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> updateAnnouncement(@PathVariable Long announcementId,
                                                @Valid @RequestBody AnnouncementUpdateRequest request)
    {
        return ResponseEntity.ok(announcementService.updateAnnouncement(announcementId, request));
    }

    @DeleteMapping("delete/{announcementId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> deleteOneAnnouncementByAnnouncementId(@PathVariable Long announcementId)
    {
        announcementService.deleteOneAnnouncementById(announcementId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}