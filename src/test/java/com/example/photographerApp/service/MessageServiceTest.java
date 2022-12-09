package com.example.photographerApp.service;

import com.example.photographerApp.exception.MessageNotFoundException;
import com.example.photographerApp.model.Message;
import com.example.photographerApp.repository.MessageRepository;
import com.example.photographerApp.request.MessageCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class MessageServiceTest 
{
    private MessageService messageService;

    private MessageRepository messageRepository;

    @BeforeEach
    public void setUp()
    {
        messageRepository = mock(MessageRepository.class);
        messageService = new MessageService(messageRepository);
    }

    @Test
    void findOneMessageById_whenMessageIdExists_shouldReturnMessage()
    {
        Message message = new Message(1L,"f_name","l_name","email","message");

        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        Message result = messageService.findOneMessageById(1L);

        assertEquals(result,message);
    }

    @Test
    void findOneMessageById_whenMessageIdDoesNotExists_shouldThrowMessageNotFoundException()
    {
        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class,
                () -> messageService.findOneMessageById(1L));
    }

    @Test
    void findAllMessage_whenAnyAmountOfMessageExists_shouldReturnMessageResponseList()
    {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message());

        when(messageRepository.findAll()).thenReturn(messages);

        List<Message> expected = messageService.getAll();

        assertEquals(expected,messages);

        verify(messageRepository).findAll();
    }

    @Test
    void createMessage_whenCreateMessage_shouldReturnMessage()
    {
        Message message = new Message(1L,"f_name","l_name","email","message");

        MessageCreateRequest request = new MessageCreateRequest();
        request.setFirstName("f_name");
        request.setLastName("l_name");
        request.setEmail("email");
        request.setMessage("message");

        when(messageRepository.save(ArgumentMatchers.any(Message.class))).thenReturn(message);

        Message createdMessage = messageService.createMessage(request);

        assertEquals(createdMessage,message);
    }
    
    @Test
    void deleteMessage_whenMessageExists_shouldDeleteUser()
    {
        Message message = new Message(1L,"f_name","l_name","email","message");

        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));

        messageService.deleteOneMessageById(1L);

        verify(messageRepository).deleteById(1L);
    }

    @Test
    void deleteMessage_whenMessageExists_shouldThrowMessageNotFoundException()
    {
        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MessageNotFoundException.class,
                () -> messageService.findOneMessageById(1L));
    }
}