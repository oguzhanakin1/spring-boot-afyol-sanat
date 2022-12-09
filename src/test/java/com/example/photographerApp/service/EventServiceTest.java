package com.example.photographerApp.service;

import com.example.photographerApp.exception.EventNotFoundException;
import com.example.photographerApp.model.Event;
import com.example.photographerApp.repository.EventRepository;
import com.example.photographerApp.request.EventCreateRequest;
import com.example.photographerApp.request.EventUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class EventServiceTest
{
    private EventService eventService;

    private EventRepository eventRepository;

    @BeforeEach
    public void setUp()
    {
        eventRepository = mock(EventRepository.class);
        eventService = new EventService(eventRepository);
    }

    @Test
    void findOneEventById_whenEventIdExists_shouldReturnEvent()
    {
        Event event = new Event(1L,"photo", "details");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Event result = eventService.findOneEventById(1L);

        assertEquals(result,event);
    }

    @Test
    void findOneEventById_whenEventIdDoesNotExists_shouldThrowEventNotFoundException()
    {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class,
                () -> eventService.findOneEventById(1L));
    }

    @Test
    void findAllEvent_whenAnyAmountOfEventExists_shouldReturnEventResponseList()
    {
        List<Event> events = new ArrayList<>();
        events.add(new Event());

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> expected = eventService.getAll();

        assertEquals(expected,events);

        verify(eventRepository).findAll();
    }

    @Test
    void createEvent_whenCreateEvent_shouldReturnEvent()
    {
        Event event = new Event(1L,"photo", "details");


        EventCreateRequest request = new EventCreateRequest();
        request.setPhoto("photo");
        request.setDetails("details");

        when(eventRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);

        Event createdEvent = eventService.createEvent(request);

        assertEquals(createdEvent,event);
    }

    @Test
    void updateEvent_whenEventExists_shouldReturnEvent()
    {
        Event event = new Event(1L,"photo", "details");

        Event editedEvent = new Event(1L,"new photo", "new details");

        EventUpdateRequest request = new EventUpdateRequest();
        request.setPhoto("new photo");
        request.setDetails("new details");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(eventRepository.save(ArgumentMatchers.any(Event.class))).thenReturn(event);

        Event updatedEvent = eventService.updateEvent(1L, request);

        verify(eventRepository).findById(1L);
        verify(eventRepository).save(editedEvent);

        assertEquals(updatedEvent,editedEvent);
    }

    @Test
    void updateEvent_whenEventDoesNotExists_shouldThrowEventNotFoundException()
    {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class,
                () -> eventService.findOneEventById(1L));
    }

    @Test
    void deleteEvent_whenEventExists_shouldDeleteUser()
    {
        Event event = new Event(1L,"eventBody", "details");

        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        eventService.deleteOneEventById(1L);

        verify(eventRepository).deleteById(1L);
    }

    @Test
    void deleteEvent_whenEventExists_shouldThrowEventNotFoundException()
    {
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class,
                () -> eventService.findOneEventById(1L));
    }
}