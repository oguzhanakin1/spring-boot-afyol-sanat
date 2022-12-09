package com.example.photographerApp.response;


import com.example.photographerApp.model.Announcement;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class AnnouncementResponse
{
    Long id;

    String announcementBody;

    LocalDateTime announcementCreateTime;
    public AnnouncementResponse(Announcement entity)
    {
        this.id = entity.getId();
        this.announcementBody = entity.getAnnouncementBody();;
        this.announcementCreateTime = entity.getAnnouncementCreateTime();
    }
}
