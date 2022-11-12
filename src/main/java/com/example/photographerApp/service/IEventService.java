package com.example.photographerApp.service;

import com.example.photographerApp.model.Event;
import com.example.photographerApp.request.EventRequest;

import java.util.List;
import java.util.Optional;

public interface IEventService {
    Event createEvent(EventRequest request);

    Event updateEvent(Long eventId, EventRequest request);

    void deleteOneEventById(Long eventId);

    List<Event> getAll();

    Optional<Event> findOneEventById(Long eventId);
}
