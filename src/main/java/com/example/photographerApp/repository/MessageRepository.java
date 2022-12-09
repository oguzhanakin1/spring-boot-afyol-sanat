package com.example.photographerApp.repository;

import com.example.photographerApp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long>
{
    
}
