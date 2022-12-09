package com.example.photographerApp.service;

import com.example.photographerApp.exception.AnnouncementNotFoundException;
import com.example.photographerApp.model.Announcement;
import com.example.photographerApp.repository.AnnouncementRepository;
import com.example.photographerApp.request.AnnouncementCreateRequest;
import com.example.photographerApp.request.AnnouncementUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class AnnouncementServiceTest
{
    private AnnouncementService announcementService;

    private AnnouncementRepository announcementRepository;

    @BeforeEach
    public void setUp()
    {
        announcementRepository = mock(AnnouncementRepository.class);
        announcementService = new AnnouncementService(announcementRepository);
    }

    @Test
    void findOneAnnouncementById_whenAnnouncementIdExists_shouldReturnAnnouncement()
    {
        Announcement announcement = new Announcement(1L,"announcementBody", null);

        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));

        Announcement result = announcementService.findOneAnnouncementById(1L);

        assertEquals(result,announcement);
    }

    @Test
    void findOneAnnouncementById_whenAnnouncementIdDoesNotExists_shouldThrowAnnouncementNotFoundException()
    {
        when(announcementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnnouncementNotFoundException.class,
                () -> announcementService.findOneAnnouncementById(1L));
    }

    @Test
    void findAllAnnouncement_whenAnyAmountOfAnnouncementExists_shouldReturnAnnouncementResponseList()
    {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement());

        when(announcementRepository.findAll()).thenReturn(announcements);

        List<Announcement> expected = announcementService.getAll();

        assertEquals(expected,announcements);

        verify(announcementRepository).findAll();
    }

    @Test
    void createAnnouncement_whenCreateAnnouncement_shouldReturnAnnouncement()
    {
        Announcement announcement = new Announcement();
        announcement.setAnnouncementBody("announcementBody");

        AnnouncementCreateRequest request = new AnnouncementCreateRequest();
        request.setAnnouncementBody("announcementBody");

        when(announcementRepository.save(ArgumentMatchers.any(Announcement.class))).thenReturn(announcement);

        Announcement createdAnnouncement = announcementService.createAnnouncement(request);

        assertEquals(createdAnnouncement,announcement);
    }

    @Test
    void updateAnnouncement_whenAnnouncementExists_shouldReturnAnnouncement()
    {
        Announcement announcement = new Announcement(1L,"announcementBody", null);

        Announcement editedAnnouncement = new Announcement(1L,
                "New announcementBody", null);

        AnnouncementUpdateRequest request = new AnnouncementUpdateRequest();

        request.setAnnouncementBody("New announcementBody");

        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));
        when(announcementRepository.save(ArgumentMatchers.any(Announcement.class))).thenReturn(announcement);

        Announcement updatedAnnouncement = announcementService.updateAnnouncement(1L, request);

        verify(announcementRepository).findById(1L);
        verify(announcementRepository).save(editedAnnouncement);

        assertEquals(updatedAnnouncement,editedAnnouncement);
    }

    @Test
    void updateAnnouncement_whenAnnouncementDoesNotExists_shouldThrowAnnouncementNotFoundException()
    {
        when(announcementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnnouncementNotFoundException.class,
                () -> announcementService.findOneAnnouncementById(1L));
    }

    @Test
    void deleteAnnouncement_whenAnnouncementExists_shouldDeleteUser()
    {
        Announcement announcement = new Announcement(1L,"announcementBody", null);

        when(announcementRepository.findById(1L)).thenReturn(Optional.of(announcement));

        announcementService.deleteOneAnnouncementById(1L);

        verify(announcementRepository).deleteById(1L);
    }

    @Test
    void deleteAnnouncement_whenAnnouncementExists_shouldThrowAnnouncementNotFoundException()
    {
        when(announcementRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AnnouncementNotFoundException.class,
                () -> announcementService.findOneAnnouncementById(1L));
    }
}