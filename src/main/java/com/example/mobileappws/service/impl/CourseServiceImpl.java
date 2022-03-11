package com.example.mobileappws.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mobileappws.io.entity.AnnouncementEntity;
import com.example.mobileappws.io.entity.CourseEntity;
import com.example.mobileappws.io.entity.RoleEntity;
import com.example.mobileappws.io.entity.TimeSlotEntity;
import com.example.mobileappws.io.entity.UserEntity;
import com.example.mobileappws.io.repository.AnnouncementRepository;
import com.example.mobileappws.io.repository.CourseRepository;
import com.example.mobileappws.io.repository.TimeSlotRepository;
import com.example.mobileappws.io.repository.UserRepository;
import com.example.mobileappws.service.CourseService;
import com.example.mobileappws.shared.dto.AddressDTO;
import com.example.mobileappws.shared.dto.AnnouncementDto;
import com.example.mobileappws.shared.dto.CourseDto;
import com.example.mobileappws.shared.dto.TimeSlotDto;
import com.example.mobileappws.shared.dto.UserDto;
import com.example.mobileappws.shared.dto.Utils;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepository;
	@Autowired
	Utils utils;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TimeSlotRepository timeSlotRepository;
	@Autowired 
	AnnouncementRepository announcementRepository;
	
	@Override
	public CourseDto createCourse(CourseDto course) {
		
				if(courseRepository.findByCourseName(course.getCourseName())!=null)
					{
					throw new RuntimeException("Record already exists.");
					}
				
				
				
				ModelMapper modelMapper=new ModelMapper();
				modelMapper.getConfiguration()
		        .setMatchingStrategy(MatchingStrategies.STRICT); //it has to match with perfectly matching names
				
				
				Collection<UserDto> listofUsers=new ArrayList<>();
				for(UserDto user : course.getUsers()) {
					UserEntity userEnt=userRepository.findByUserId(user.getUserId());
					UserDto savedUserDto=modelMapper.map(userEnt, UserDto.class);
					listofUsers.add(savedUserDto);	
				}
				
				course.setUsers(listofUsers);
				
	
				
				
				CourseEntity courseEntity=modelMapper.map(course, CourseEntity.class);
				System.out.println("----------------------------------------------");
				//worst case timeslot kendin kaydet yoksa eğer sonra al
				
/*			
				for(TimeSlotDto timeslot:course.getTimeslots()) {
					if(timeSlotRepository.findTimeSlotByDayAndSlot(timeslot.getDay(), timeslot.getSlot())==null) {
						TimeSlotEntity timeSlotEntity=modelMapper.map(timeslot, TimeSlotEntity.class);
						courseEntity.getTimeslots().add(timeSlotRepository.save(timeSlotEntity));
					}
					else {
						TimeSlotEntity timeSlotEntity=timeSlotRepository.findTimeSlotByDayAndSlot(timeslot.getDay(), timeslot.getSlot());
						courseEntity.getTimeslots().add(timeSlotEntity);
					}
					
					
				}*/
				
			
				
				
				
				
				String publicCourseId=utils.generateCourseId(30);
				courseEntity.setCourseId(publicCourseId);
				
				
				
				CourseEntity storedCourseDetails= courseRepository.save(courseEntity);
				//we are storing timeslot info every time because in our domain the time description is dependent on the academician
				CourseDto returnValue=modelMapper.map(storedCourseDetails, CourseDto.class);

				return returnValue;
	}

	@Override
	public CourseDto updateCourse(CourseDto course) {

		
		if(courseRepository.findByCourseName(course.getCourseName())==null)
		{
		throw new RuntimeException("Course not exists.");
		}
		CourseEntity courseDetails=courseRepository.findByCourseName(course.getCourseName());
		
		
		
		ModelMapper modelMapper=new ModelMapper();
		
		for(UserDto user : course.getUsers()) {
			UserEntity userEnt=userRepository.findByUserId(user.getUserId());
			
			if(!courseDetails.getUsers().contains(userEnt)) {
				courseDetails.getUsers().add(userEnt);
			}
			
		}
		
		
		CourseEntity updatedCourse=courseRepository.save(courseDetails);

		
		CourseDto returnValue=modelMapper.map(updatedCourse, CourseDto.class);

		return returnValue;
	}

	@Override
	public CourseDto deleteStudentFromCourse(CourseDto course, String studentId) {
		
		CourseEntity courseDetails=courseRepository.findByCourseName(course.getCourseName());
		UserEntity deletingUser=userRepository.findByUserId(studentId);
		if(courseDetails.getCourseName()==null)
		{
		throw new RuntimeException("Course not exists.");
		}
		if(!courseDetails.getUsers().contains(deletingUser)) {
			throw new RuntimeException("Student not exists in the course.");
		}
		ModelMapper modelMapper=new ModelMapper();
		
		courseDetails.getUsers().remove(deletingUser);
		
		CourseEntity updatedCourse=courseRepository.save(courseDetails);
		CourseDto returnValue=modelMapper.map(updatedCourse, CourseDto.class);
		
		return returnValue;
	}

	@Override
	public List<CourseDto> getMyCourses(String userId) {
		
		List<CourseDto> returnValue=new ArrayList<>();
		
		UserEntity userEntity=userRepository.findByUserId(userId);
		
		List<CourseEntity> courseEntities=courseRepository.getCoursesByUserId(userEntity.getId());
		
		
		ModelMapper modelMapper=new ModelMapper();
		
		for(CourseEntity courseEntity : courseEntities) {
			
			returnValue.add(modelMapper.map(courseEntity, CourseDto.class));
			
		}
		
		
		
		
		return returnValue;
	}
	@Override
	public List<AnnouncementDto> getAnnouncementsOfTheCourse(String courseId) {
		
		List<AnnouncementDto> returnValue = new ArrayList<>();
		List<AnnouncementEntity> listOfAnnouncement = announcementRepository.getAnnoucementsOfTheCourseByCourseId(courseId);
		ModelMapper modelMapper=new ModelMapper();
		
		for(AnnouncementEntity announcementEntity : listOfAnnouncement) {
			
			returnValue.add(modelMapper.map(announcementEntity, AnnouncementDto.class));
		}
		
		
		return returnValue;
	}


	@Override
	public List<UserDto> getParticipantsOfTheCourse(String courseId) {

		
		//getUsersOfTheCourseByCourseId
		List<UserDto> returnValue=new ArrayList<>();
		
		List<UserEntity> listOfUsers=userRepository.getUsersOfTheCourseByCourseId(courseId);
		ModelMapper modelMapper=new ModelMapper();
		
		for(UserEntity userEntity : listOfUsers) {
			
			returnValue.add(modelMapper.map(userEntity, UserDto.class));
		}
		
		
		
		
		
		return returnValue;
	}

}
