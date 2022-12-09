package com.example.photographerApp.controller;

import com.example.photographerApp.model.Announcement;
import com.example.photographerApp.repository.AnnouncementRepository;
import com.example.photographerApp.request.AnnouncementCreateRequest;
import com.example.photographerApp.request.AnnouncementUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port = 0"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class AnnouncementControllerTest
{
    private MockMvc mockMvc;

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        announcementRepository.deleteAll();
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testCreateAnnouncementWithAValidAuthority_ShouldCreateAnnouncementAndReturnAnnouncement()
            throws Exception
    {
        AnnouncementCreateRequest request = new AnnouncementCreateRequest();
        request.setAnnouncementBody("new body");

        this.mockMvc
                .perform(post("/api/announcements/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.announcementBody", is("new body")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testCreateAnnouncementWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        AnnouncementCreateRequest request = new AnnouncementCreateRequest();
        request.setAnnouncementBody("new body");

        this.mockMvc
                .perform(post("/api/announcements/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testUpdateAnnouncementWithAValidAuthority_ShouldUpdateAnnouncementAndReturnAnnouncement()
            throws Exception
    {
        Announcement announcement = announcementRepository.save(
                new Announcement(1L, "body", null));
        AnnouncementUpdateRequest request = new AnnouncementUpdateRequest();
        request.setAnnouncementBody("new body");

        this.mockMvc
                .perform(put("/api/announcements/update/{announcementId}", announcement.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.announcementBody", is("new body")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testUpdateAnnouncementWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {

        Announcement announcement = announcementRepository.save(
                new Announcement(1L, "body", null));
        AnnouncementUpdateRequest request = new AnnouncementUpdateRequest();
        request.setAnnouncementBody("new body");

        this.mockMvc
                .perform(put("/api/announcements/update/{announcementId}", announcement.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testDeleteAnnouncementWithAValidAuthority_ShouldDeleteAnnouncement()
            throws Exception
    {
        Announcement announcement = announcementRepository.save(
                new Announcement(1L, "body", null));

        this.mockMvc
                .perform(delete("/api/announcements/delete/{announcementId}", announcement.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid authority
    @WithMockUser(authorities = "manager")
    public void testDeleteAnnouncementWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        Announcement announcement = announcementRepository.save(
                new Announcement(1L, "body", null));

        this.mockMvc
                .perform(delete("/api/announcements/delete/{announcementId}", announcement.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test//valid authority
    public void testGetAllAnnouncementsWhenRequested_ShouldReturnListOfAnnouncementResponses()
            throws Exception
    {
        List<Announcement> announcements = new ArrayList<>();
        announcements.add(new Announcement(1L,"body",null));
        announcements.add(new Announcement(2L,"new body", null));
        announcementRepository.saveAll(announcements);

        this.mockMvc.perform(get("/api/announcements/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test//valid id
    public void testGetAllAnnouncementWithAValidId_ShouldReturnAnnouncementResponse()
            throws Exception
    {
        Announcement announcement = announcementRepository.save(new Announcement(1L,"body",null));

        this.mockMvc.perform(get("/api/announcements/get/{announcementId}", announcement.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.announcementBody", is("body")));

    }

    @Test//invalid id
    public void testGetAllAnnouncementWithAnInvalidId_ShouldReturnHttpStatus_NotFound()
            throws Exception
    {
        this.mockMvc.perform(get("/api/announcements/get/{announcementId}", 0L))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}