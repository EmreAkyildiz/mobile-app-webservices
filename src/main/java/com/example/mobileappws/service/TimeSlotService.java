package com.example.mobileappws.service;

import java.util.List;

import com.example.mobileappws.shared.dto.TimeSlotDto;

public interface TimeSlotService {
	List<TimeSlotDto> getTimeSlots(String courseId);
}
