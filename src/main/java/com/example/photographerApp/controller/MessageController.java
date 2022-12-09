package com.example.photographerApp.controller;

import com.example.photographerApp.model.Message;
import com.example.photographerApp.request.MessageCreateRequest;
import com.example.photographerApp.response.MessageResponse;
import com.example.photographerApp.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/messages")
public class MessageController
{
    private final MessageService messageService;

    public MessageController(MessageService messageService)
    {
        this.messageService = messageService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin', 'editor', 'manager')")
    public ResponseEntity<List<MessageResponse>> findAll()
    {
        return ResponseEntity.ok(messageService.getAll().stream().map(
                message -> new MessageResponse(message)).collect(Collectors.toList()));
    }

    @GetMapping("get/{messageId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor', 'manager')")
    public ResponseEntity<?> findOneMessageById(@PathVariable Long messageId)
    {
        Message message = messageService.findOneMessageById(messageId);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PostMapping("create")
    public ResponseEntity<?> createMessage(@RequestBody MessageCreateRequest request)
    {
        return new ResponseEntity<>(messageService.createMessage(request), HttpStatus.CREATED);
    }

    @DeleteMapping("delete/{messageId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteOneMessageByMessageId(@PathVariable Long messageId)
    {
        messageService.deleteOneMessageById(messageId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
