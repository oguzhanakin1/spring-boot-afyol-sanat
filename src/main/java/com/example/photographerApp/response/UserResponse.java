package com.example.photographerApp.response;

import com.example.photographerApp.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserResponse
{
    Long id;
    String firstName;
    String lastName;

    String auth;

    public UserResponse(User entity)
    {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.auth = entity.getAuthority().getName();
    }
}
