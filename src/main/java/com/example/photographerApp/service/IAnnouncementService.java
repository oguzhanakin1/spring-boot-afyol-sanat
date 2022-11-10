package com.example.photographerApp.service;

import com.example.photographerApp.model.Announcement;
import com.example.photographerApp.request.AnnouncementRequest;

import java.util.List;
import java.util.Optional;

public interface IAnnouncementService
{
    Announcement createAnnouncement(AnnouncementRequest request);

    Announcement updateAnnouncement(Long announcementId, AnnouncementRequest request);

    void deleteOneAnnouncementById(Long announcementId);

    List<Announcement> getAll();

    Optional<Announcement> findOneAnnouncementById(Long announcementId);
}
