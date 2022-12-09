package com.example.photographerApp.service;

import com.example.photographerApp.exception.AnnouncementNotFoundException;
import com.example.photographerApp.model.Announcement;
import com.example.photographerApp.repository.AnnouncementRepository;
import com.example.photographerApp.request.AnnouncementCreateRequest;
import com.example.photographerApp.request.AnnouncementUpdateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AnnouncementService
{
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository)
    {
        this.announcementRepository = announcementRepository;
    }

    public Announcement createAnnouncement(AnnouncementCreateRequest request)
    {
        Announcement announcement = new Announcement();
        announcement.setAnnouncementBody(request.getAnnouncementBody());
        announcement.setAnnouncementCreateTime(LocalDateTime.now());
        return announcementRepository.save(announcement);
    }

    public Announcement updateAnnouncement(Long announcementId, AnnouncementUpdateRequest request)
    {
        Announcement announcementToEdit = announcementRepository.findById(announcementId).orElseThrow();
        announcementToEdit.setAnnouncementBody(request.getAnnouncementBody());

        return announcementRepository.save(announcementToEdit);
    }

    public void deleteOneAnnouncementById(Long announcementId)
    {
        announcementRepository.deleteById(announcementId);
    }

    public List<Announcement> getAll()
    {
        return announcementRepository.findAll();
    }

    public Announcement findOneAnnouncementById(Long announcementId)
    {
        return announcementRepository.findById(announcementId)
                .orElseThrow(()->
                        new AnnouncementNotFoundException
                                ("Announcement not found with id: " + announcementId));
    }
}
