package com.example.photographerApp.repository;

import com.example.photographerApp.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement,Long>
{
}
