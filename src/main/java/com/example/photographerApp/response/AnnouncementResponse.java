package com.example.photographerApp.response;


import com.example.photographerApp.model.Announcement;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementResponse
{
    Long id;

    String announcementBody;

    LocalDateTime announcementCreateTime;
    public AnnouncementResponse(Announcement announcement)
    {
        this.id = announcement.getId();
        this.announcementBody = announcement.getAnnouncementBody();;
        this.announcementCreateTime = announcement.getAnnouncementCreateTime();
    }
}
