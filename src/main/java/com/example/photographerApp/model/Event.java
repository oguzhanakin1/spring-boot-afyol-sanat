package com.example.photographerApp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "events")
@Data
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "photo", columnDefinition = "text")
    private String photo;

    @Column(name = "details", columnDefinition = "text")
    private String details;
}
