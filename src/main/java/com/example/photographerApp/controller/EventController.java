package com.example.photographerApp.controller;


import com.example.photographerApp.model.Event;
import com.example.photographerApp.request.EventCreateRequest;
import com.example.photographerApp.request.EventUpdateRequest;
import com.example.photographerApp.response.EventResponse;
import com.example.photographerApp.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/events")
public class EventController
{
    private final EventService eventService;

    public EventController(EventService eventService)
    {
        this.eventService = eventService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventResponse>> findAll()
    {
        return ResponseEntity.ok(eventService.getAll().stream().map(
                event -> new EventResponse(event)).collect(Collectors.toList()));
    }

    @GetMapping("get/{EventId}")
    public ResponseEntity<?> findOneEventById(@PathVariable Long EventId)
    {
        Event Event = eventService.findOneEventById(EventId);
        return ResponseEntity.ok(new EventResponse(Event));
    }

    @PostMapping("create")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> createEvent(@RequestBody EventCreateRequest request)
    {
        return new ResponseEntity<>(eventService.createEvent(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{EventId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> updateEvent(@PathVariable Long EventId,
                                         @RequestBody EventUpdateRequest request)
    {
        return ResponseEntity.ok(eventService.updateEvent(EventId, request));
    }

    @DeleteMapping("delete/{EventId}")
    @PreAuthorize("hasAnyAuthority('admin', 'editor')")
    public ResponseEntity<?> deleteOneEventByEventId(@PathVariable Long EventId)
    {
        eventService.deleteOneEventById(EventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
