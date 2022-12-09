package com.example.photographerApp.controller;

import com.example.photographerApp.model.Event;
import com.example.photographerApp.repository.EventRepository;
import com.example.photographerApp.request.EventCreateRequest;
import com.example.photographerApp.request.EventUpdateRequest;
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
class EventControllerTest
{
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        eventRepository.deleteAll();
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testCreateEventWithAValidAuthority_ShouldCreateEventAndReturnEvent()
            throws Exception
    {
        EventCreateRequest request = new EventCreateRequest();
        request.setPhoto("photo");
        request.setDetails("details");

        this.mockMvc
                .perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.photo", is("photo")))
                .andExpect(jsonPath("$.details", is("details")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testCreateEventWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        EventCreateRequest request = new EventCreateRequest();
        request.setPhoto("photo");
        request.setDetails("details");

        this.mockMvc
                .perform(post("/api/events/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testUpdateEventWithAValidAuthority_ShouldUpdateEventAndReturnEvent()
            throws Exception
    {
        Event event = eventRepository.save(new Event(1L, "photo", "details"));
        EventUpdateRequest request = new EventUpdateRequest();
        request.setPhoto("new photo");
        request.setDetails("new details");

        this.mockMvc
                .perform(put("/api/events/update/{eventId}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.photo", is("new photo")))
                .andExpect(jsonPath("$.details", is("new details")));
    }

    @Test//invalid authority
    @WithMockUser(authorities = "manager")
    public void testUpdateEventWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {

        Event event = eventRepository.save(new Event(1L, "photo", "details"));
        EventUpdateRequest request = new EventUpdateRequest();
        request.setPhoto("new photo");
        request.setDetails("new details");

        this.mockMvc
                .perform(put("/api/events/update/{eventId}", event.getId())
                        .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test//valid authority
    @WithMockUser(authorities = "admin")
    public void testDeleteEventWithAValidAuthority_ShouldDeleteEvent()
            throws Exception
    {
        Event event = eventRepository.save(new Event(1L, "photo", "details"));

        this.mockMvc
                .perform(delete("/api/events/delete/{eventId}", event.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid authority
    @WithMockUser(authorities = "manager")
    public void testDeleteEventWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        Event event = eventRepository.save(new Event(1L, "photo", "details"));

        this.mockMvc
                .perform(delete("/api/events/delete/{eventId}", event.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test//valid authority
    public void testGetAllEventsWhenRequested_ShouldReturnListOfEventResponses()
            throws Exception
    {
        List<Event> events = new ArrayList<>();
        events.add(new Event(1L, "photo", "details"));
        events.add( new Event(2L, "new photo", "new details"));
        eventRepository.saveAll(events);

        this.mockMvc.perform(get("/api/events/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//valid id
    public void testGetEventWithAValidId_ShouldReturnEventResponse()
            throws Exception
    {
        Event event = eventRepository.save(new Event(1L, "photo", "details"));

        this.mockMvc.perform(get("/api/events/get/{eventId}", event.getId()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.photo", is("photo")))
                .andExpect(jsonPath("$.details", is("details")));
    }

    @Test//invalid id
    public void testGetEventWithAnInvalidId_ShouldReturnHttpStatus_NotFound()
            throws Exception
    {
        this.mockMvc.perform(get("/api/events/get/{EventId}", 0L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}