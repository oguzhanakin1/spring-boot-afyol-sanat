package com.example.photographerApp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "photos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_url",columnDefinition = "text" , nullable = false)
    private String photoUrl;

    @Column(name = "photo_description")
    private String photoDescription;

    @Column(name = "photographer_name")
    private String photographerName;

    @Column(name="photo_create_time")
    private LocalDateTime photoCreateTime;
}
