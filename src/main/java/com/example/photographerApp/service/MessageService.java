package com.example.photographerApp.service;

import com.example.photographerApp.exception.MessageNotFoundException;
import com.example.photographerApp.model.Message;
import com.example.photographerApp.repository.MessageRepository;
import com.example.photographerApp.request.MessageCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(MessageCreateRequest request)
    {
        Message message = new Message();

        message.setFirstName(request.getFirstName());
        message.setLastName(request.getLastName());
        message.setEmail(request.getEmail());
        message.setMessage(request.getMessage());

        return messageRepository.save(message);
    }

    public void deleteOneMessageById(Long messageId)
    {
        messageRepository.deleteById(messageId);
    }

    public List<Message> getAll()
    {
        return messageRepository.findAll();
    }

    public Message findOneMessageById(Long messageId)
    {
        return messageRepository.findById(messageId)
                .orElseThrow(() ->
                        new MessageNotFoundException
                                ("Message not found with id:" + messageId));
    }
}
