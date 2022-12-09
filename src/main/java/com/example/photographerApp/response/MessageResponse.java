package com.example.photographerApp.response;

import com.example.photographerApp.model.Message;
import lombok.Data;

@Data
public class MessageResponse
{
    String firstName;

    String lastName;

    String email;

    String message;

    public MessageResponse(Message entity)
    {
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.email = entity.getEmail();
        this.message = entity.getMessage();
    }
}
