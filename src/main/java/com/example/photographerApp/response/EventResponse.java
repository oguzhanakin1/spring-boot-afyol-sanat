package com.example.photographerApp.response;

import com.example.photographerApp.model.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class EventResponse
{
    Long id;

    String photo;

    String details;

    public EventResponse(Event entity)
    {
        this.id = entity.getId();
        this.photo = entity.getPhoto();
        this.details = entity.getDetails();
    }
}
