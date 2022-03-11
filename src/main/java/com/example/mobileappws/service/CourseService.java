package com.example.mobileappws.service;

import java.util.List;

import com.example.mobileappws.shared.dto.AnnouncementDto;
import com.example.mobileappws.shared.dto.CourseDto;
import com.example.mobileappws.shared.dto.UserDto;

public interface CourseService {

	CourseDto createCourse(CourseDto course);
	CourseDto updateCourse(CourseDto course);
	CourseDto deleteStudentFromCourse(CourseDto course,String studentId);
	List<CourseDto> getMyCourses(String userId);
	List<UserDto> getParticipantsOfTheCourse(String courseId);
	List<AnnouncementDto> getAnnouncementsOfTheCourse(String id);
	
	
}
