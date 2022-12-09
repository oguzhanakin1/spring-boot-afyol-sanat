package com.example.photographerApp.response;

import com.example.photographerApp.model.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class MemberResponse
{
    String firstName;

    String lastName;

    String memberRole;

    public MemberResponse(Member entity)
    {
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.memberRole = entity.getMemberRole();
    }
}
