package com.example.photographerApp.controller;

import com.example.photographerApp.model.Photo;
import com.example.photographerApp.repository.PhotoRepository;
import com.example.photographerApp.request.PhotoCreateRequest;
import com.example.photographerApp.request.PhotoUpdateRequest;
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

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "server-port = 0"
})
@RunWith(SpringRunner.class)
@DirtiesContext
class PhotoControllerTest
{
    private MockMvc mockMvc;

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        photoRepository.deleteAll();
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testCreatePhotoWithAValidAuthority_ShouldCreatePhotoAndReturnPhoto()
            throws Exception
    {
        PhotoCreateRequest request = new PhotoCreateRequest();
        request.setPhotoUrl("url");
        request.setPhotoDescription("description");
        request.setPhotographerName("photographer");

        this.mockMvc
                .perform(post("/api/photos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.photoUrl", is("url")))
                .andExpect(jsonPath("$.photoDescription", is("description")))
                .andExpect(jsonPath("$.photographerName", is("photographer")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testCreatePhotoWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        PhotoCreateRequest request = new PhotoCreateRequest();
        request.setPhotoUrl("url");
        request.setPhotoDescription("description");
        request.setPhotographerName("photographer");

        this.mockMvc
                .perform(post("/api/photos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testUpdatePhotoWithAValidAuthority_ShouldUpdatePhotoAndReturnPhoto()
            throws Exception
    {
        Photo photo = photoRepository.save(new Photo(1L, "url", "description", "photographer", null));
        PhotoUpdateRequest request = new PhotoUpdateRequest();
        request.setPhotoUrl("new url");
        request.setPhotoDescription("new description");
        request.setPhotographerName("new photographer");

        this.mockMvc
                .perform(put("/api/photos/update/{photoId}", photo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.photoUrl", is("new url")))
                .andExpect(jsonPath("$.photoDescription", is("new description")))
                .andExpect(jsonPath("$.photographerName", is("new photographer")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testUpdatePhotoWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {

        Photo photo = photoRepository.save(new Photo(1L, "url", "description", "photographer", null));
        PhotoUpdateRequest request = new PhotoUpdateRequest();
        request.setPhotoUrl("new url");
        request.setPhotoDescription("new description");
        request.setPhotographerName("new photographer");

        this.mockMvc
                .perform(put("/api/photos/update/{photoId}", photo.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testDeletePhotoWithAValidAuthority_ShouldDeletePhoto()
            throws Exception
    {
        Photo photo = photoRepository.save(new Photo(1L, "url", "description", "photographer", null));

        this.mockMvc
                .perform(delete("/api/photos/delete/{photoId}", photo.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid authority
    @WithMockUser(authorities = "manager")
    public void testDeletePhotoWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        Photo photo = photoRepository.save(new Photo(1L, "url", "description", "photographer", null));

        this.mockMvc
                .perform(delete("/api/photos/delete/{photoId}", photo.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test//valid authority
    public void testGetAllPhotosWhenRequested_ShouldReturnListOfPhotoResponses()
            throws Exception
    {
        List<Photo> photos = new ArrayList<>();
        photos.add(new Photo(1L, "url", "description", "photographer", null));
        photos.add(new Photo(2L, "new url", "new description", "new photographer", null));
        photoRepository.saveAll(photos);

        this.mockMvc.perform(get("/api/photos/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid id
    public void testGetPhotoWithAValidId_ShouldReturnPhotoResponse()
            throws Exception
    {
        Photo photo = photoRepository.save(new Photo(1L, "url", "description", "photographer", null));

        this.mockMvc.perform(get("/api/photos/get/{photoId}", photo.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photoUrl", is("url")))
                .andExpect(jsonPath("$.photoDescription", is("description")))
                .andExpect(jsonPath("$.photographerName", is("photographer")));
    }

    @Test//invalid id
    public void testGetPhotoWithAnInvalidId_ShouldReturnHttpStatus_NotFound()
            throws Exception
    {
        this.mockMvc.perform(get("/api/photos/get/{photoId}", 0L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}