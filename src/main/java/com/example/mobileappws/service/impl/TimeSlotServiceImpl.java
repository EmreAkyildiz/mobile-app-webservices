package com.example.mobileappws.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobileappws.io.entity.AddressEntity;
import com.example.mobileappws.io.entity.CourseEntity;
import com.example.mobileappws.io.entity.TimeSlotEntity;
import com.example.mobileappws.io.entity.UserEntity;
import com.example.mobileappws.io.repository.CourseRepository;
import com.example.mobileappws.io.repository.TimeSlotRepository;
import com.example.mobileappws.service.TimeSlotService;
import com.example.mobileappws.shared.dto.TimeSlotDto;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	TimeSlotRepository timeSlotRepository;
	
	@Override
	public List<TimeSlotDto> getTimeSlots(String courseId) {
		List<TimeSlotDto> returnValue=new ArrayList<>();
		ModelMapper modelMapper=new ModelMapper();
		
		CourseEntity courseEntity=courseRepository.findByCourseId(courseId);
		if(courseEntity==null) return returnValue;
		
		Iterable<TimeSlotEntity> timeSlots=timeSlotRepository.getTimeSlotsByCourseId(courseEntity.getId());
		
		for(TimeSlotEntity timeSlotEntity:timeSlots) {
			returnValue.add(modelMapper.map(timeSlotEntity,TimeSlotDto.class));
		}
		
		return returnValue;
	}

}
