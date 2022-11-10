package com.example.photographerApp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
@Data
public class Announcement
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "announcement_body", nullable = false)
    private String announcementBody;

    @Column(name="announcement_create_time")
    private LocalDateTime announcementCreateTime;
}
