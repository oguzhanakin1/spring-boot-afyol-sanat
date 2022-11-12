package com.example.photographerApp.service;

import com.example.photographerApp.model.Event;
import com.example.photographerApp.repository.EventRepository;
import com.example.photographerApp.request.EventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService
{
    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository)
    {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(EventRequest request)
    {
        Event event = new Event();
        event.setPhoto(request.getPhoto());
        event.setDetails(request.getDetails());
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long eventId, EventRequest request)
    {
        Event eventToEdit = eventRepository.findById(eventId).orElseThrow();
        eventToEdit.setPhoto(request.getPhoto());
        eventToEdit.setDetails(request.getDetails());
        return eventRepository.save(eventToEdit);
    }

    @Override
    public void deleteOneEventById(Long eventId)
    {
        eventRepository.deleteById(eventId);
    }

    @Override
    public List<Event> getAll()
    {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> findOneEventById(Long eventId)
    {
        return eventRepository.findById(eventId);
    }
}
