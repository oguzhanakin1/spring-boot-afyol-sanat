package com.example.photographerApp.service;

import com.example.photographerApp.exception.EventNotFoundException;
import com.example.photographerApp.model.Event;
import com.example.photographerApp.repository.EventRepository;
import com.example.photographerApp.request.EventCreateRequest;
import com.example.photographerApp.request.EventUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventService
{
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    public Event createEvent(EventCreateRequest request)
    {
        Event event = new Event();
        event.setPhoto(request.getPhoto());
        event.setDetails(request.getDetails());
        return eventRepository.save(event);
    }

    public Event updateEvent(Long eventId, EventUpdateRequest request)
    {
        Event eventToEdit = eventRepository.findById(eventId).orElseThrow();
        eventToEdit.setPhoto(request.getPhoto());
        eventToEdit.setDetails(request.getDetails());
        return eventRepository.save(eventToEdit);
    }

    public void deleteOneEventById(Long eventId)
    {
        eventRepository.deleteById(eventId);
    }

    public List<Event> getAll()
    {
        return eventRepository.findAll();
    }

    public Event findOneEventById(Long eventId)
    {
        return eventRepository.findById(eventId)
                .orElseThrow(()->
                        new EventNotFoundException
                                ("Event not found with id: " + eventId));
    }
}
