package com.example.photographerApp.controller;

import com.example.photographerApp.model.Message;
import com.example.photographerApp.repository.MessageRepository;
import com.example.photographerApp.request.MessageCreateRequest;
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
class MessageControllerTest
{
    private MockMvc mockMvc;

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        messageRepository.deleteAll();
    }

    @Test//Create(no authority needed)
    public void testCreateMessage_ShouldCreateMessageAndReturnMessage()
            throws Exception
    {
        MessageCreateRequest request = new MessageCreateRequest();
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setEmail("email");
        request.setMessage("message");

        this.mockMvc
                .perform(post("/api/messages/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.email", is("email")))
                .andExpect(jsonPath("$.message", is("message")));
    }

    @Test//Delete with valid authority
    @WithMockUser(authorities = "admin")
    public void testDeleteMessageWithAValidAuthority_ShouldDeleteMessage()
            throws Exception
    {
        Message message = messageRepository.save(new Message(1L, "firstName", "lastName", "email", "message"));

        this.mockMvc
                .perform(delete("/api/messages/delete/{messageId}", message.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//Delete with invalid authority
    @WithMockUser(authorities = "manager")
    public void testDeleteMessageWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        Message message = messageRepository.save(new Message(1L, "firstName", "lastName", "email", "message"));

        this.mockMvc
                .perform(delete("/api/messages/delete/{messageId}", message.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test//Get All with valid authority
    @WithMockUser(authorities = "manager")
    public void testGetAllMessagesWithValidAuthority_ShouldReturnListOfMessageResponses()
            throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1L, "firstName", "lastName", "email", "message"));
        messages.add(new Message(2L, "new firstName", "new lastName", "new email", "new message"));
        messageRepository.saveAll(messages);

        this.mockMvc.perform(get("/api/messages/all"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test//Get All with invalid authority
    @WithMockUser(authorities = "user")
    public void testGetAllMessagesWithAnInvalidAuthority_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1L, "firstName", "lastName", "email", "message"));
        messages.add(new Message(2L, "new firstName", "new lastName", "new email", "new message"));
        messageRepository.saveAll(messages);

        this.mockMvc.perform(get("/api/messages/all"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
    @Test//Get with valid id
    @WithMockUser(authorities = "manager")
    public void testGetMessageWithAValidId_ShouldReturnMessageResponse()
            throws Exception
    {
        Message message = messageRepository.save(new Message(1L, "firstName", "lastName", "email", "message"));

        this.mockMvc.perform(get("/api/messages/get/{messageId}", message.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.email", is("email")))
                .andExpect(jsonPath("$.message", is("message")));
    }

    @Test//Get with valid id
    @WithMockUser(authorities = "user")
    public void testGetMessageWithAnInvalidId_ShouldReturnHTTPStatus_Unauthorized()
            throws Exception
    {
        Message message = messageRepository.save(new Message(1L, "firstName", "lastName", "email", "message"));

        this.mockMvc.perform(get("/api/messages/get/{messageId}", message.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test//invalid id
    @WithMockUser(authorities = "manager")
    public void testGetMessageWithAnInvalidId_ShouldReturnHttpStatus_NotFound()
            throws Exception
    {
        this.mockMvc.perform(get("/api/messages/get/{messageId}", 0L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}