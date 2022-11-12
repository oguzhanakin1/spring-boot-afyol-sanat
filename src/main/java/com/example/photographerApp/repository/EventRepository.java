package com.example.photographerApp.repository;

import com.example.photographerApp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long>
{
}
