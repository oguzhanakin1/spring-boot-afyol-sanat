package com.example.photographerApp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Data
public class Member
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "member_role", nullable = false)
    private String memberRole;
}
