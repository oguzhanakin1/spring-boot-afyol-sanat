package com.example.photographerApp.service;

import com.example.photographerApp.model.Announcement;
import com.example.photographerApp.repository.AnnouncementRepository;
import com.example.photographerApp.request.AnnouncementRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService implements IAnnouncementService
{
    private AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository)
    {
        this.announcementRepository = announcementRepository;
    }

    @Override
    public Announcement createAnnouncement(AnnouncementRequest request)
    {
        Announcement announcement = new Announcement();
        announcement.setAnnouncementBody(request.getAnnouncementBody());
        announcement.setAnnouncementCreateTime(LocalDateTime.now());
        return announcementRepository.save(announcement);
    }

    @Override
    public Announcement updateAnnouncement(Long announcementId, AnnouncementRequest request)
    {
        Announcement announcementToEdit = announcementRepository.findById(announcementId).orElseThrow();
        announcementToEdit.setAnnouncementBody(request.getAnnouncementBody());

        return announcementRepository.save(announcementToEdit);
    }

    @Override
    public void deleteOneAnnouncementById(Long announcementId)
    {
        announcementRepository.deleteById(announcementId);
    }

    @Override
    public List<Announcement> getAll()
    {
        return announcementRepository.findAll();
    }

    @Override
    public Optional<Announcement> findOneAnnouncementById(Long announcementId)
    {
        return announcementRepository.findById(announcementId);
    }
}
