package com.example.photographerApp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "authorities")
@Data
public class Authority
{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "authority", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> user;
}
