package com.example.mobileappws.service;

import com.example.mobileappws.shared.dto.AnnouncementDto;

public interface AnnouncementService {
	AnnouncementDto createAnnouncement(AnnouncementDto announcement, String courseName,String userEmail);
}
