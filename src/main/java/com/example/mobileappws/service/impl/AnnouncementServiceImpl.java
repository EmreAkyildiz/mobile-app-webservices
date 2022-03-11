package com.example.mobileappws.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobileappws.io.entity.AnnouncementEntity;
import com.example.mobileappws.io.entity.CourseEntity;
import com.example.mobileappws.io.entity.UserEntity;
import com.example.mobileappws.io.repository.AnnouncementRepository;
import com.example.mobileappws.io.repository.CourseRepository;
import com.example.mobileappws.io.repository.UserRepository;
import com.example.mobileappws.service.AnnouncementService;
import com.example.mobileappws.shared.dto.AnnouncementDto;
import com.example.mobileappws.shared.dto.CourseDto;
import com.example.mobileappws.shared.dto.UserDto;
import com.example.mobileappws.shared.dto.Utils;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

	
	@Autowired
	AnnouncementRepository announcementRepository;
	@Autowired
	Utils utils;
	@Autowired
	CourseRepository courseRepository;
	@Autowired 
	UserRepository userRepository;
	
	@Override
	public AnnouncementDto createAnnouncement(AnnouncementDto announcement,String courseName, String userEmail) {
		
		ModelMapper modelMapper = new ModelMapper();
	
	AnnouncementEntity announcementEntity = modelMapper.map(announcement, AnnouncementEntity.class);
	String publicAnnoncementId = utils.generateAnnouncementId(10);
	announcementEntity.setAnnouncementId(publicAnnoncementId);
	if(courseRepository.findByCourseName(courseName)==null)
	{
	throw new RuntimeException("No such a course");
	}
	if(userRepository.findByEmail(userEmail)==null) {
		throw new RuntimeException("no such a email");
	}
	CourseEntity  courseEntity = courseRepository.findByCourseName(courseName);
	announcementEntity.setCourseDetails(courseEntity);
	
	UserEntity userEntity = userRepository.findByEmail(userEmail);
	announcementEntity.setUserDetails(userEntity);
	
	
	AnnouncementEntity storedAnnouncementDetails = announcementRepository.save(announcementEntity);
	AnnouncementDto returnValue = modelMapper.map(storedAnnouncementDetails, AnnouncementDto.class);
	return returnValue;
 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*Collection<AnnouncementDto> listofAnnouncements = new ArrayList<>(); 
	CourseEntity targetCourse = courseRepository.findByCourseName(announcement.getCourseDetails().getCourseName());
	CourseDto targetCourseDto = modelMapper.map(targetCourse, CourseDto.class);*/
	
	/*for (AnnouncementDto announcementl : targetCourseDto.getAnnouncements()) {
		AnnouncementEntity announcementEntity = announcementRepository.findByAnnouncementId(announcementl.getAnnouncementId());
		AnnouncementDto savedAnnouncementDto = modelMapper.map(announcementEntity, AnnouncementDto.class);
		listofAnnouncements.add(savedAnnouncementDto);
	}
	targetCourseDto.setAnnouncements(listofAnnouncements);
	
	AnnouncementEntity announcementEntity = modelMapper.map(targetCourseDto, null)
}
*/
}