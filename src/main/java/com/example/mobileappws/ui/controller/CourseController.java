package com.example.mobileappws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobileappws.exceptions.CourseServiceException;
import com.example.mobileappws.exceptions.UserServiceException;
import com.example.mobileappws.service.CourseService;
import com.example.mobileappws.service.TimeSlotService;
import com.example.mobileappws.shared.dto.AddressDTO;
import com.example.mobileappws.shared.dto.AnnouncementDto;
import com.example.mobileappws.shared.dto.CourseDto;
import com.example.mobileappws.shared.dto.Roles;
import com.example.mobileappws.shared.dto.TimeSlotDto;
import com.example.mobileappws.shared.dto.UserDto;
import com.example.mobileappws.ui.model.request.CourseRequestModel;
import com.example.mobileappws.ui.model.request.UserDetailsRequestModel;
import com.example.mobileappws.ui.model.response.AddressesRest;
import com.example.mobileappws.ui.model.response.AnnouncementRest;
import com.example.mobileappws.ui.model.response.CourseRest;
import com.example.mobileappws.ui.model.response.ErrorMessages;
import com.example.mobileappws.ui.model.response.TimeSlotRest;
import com.example.mobileappws.ui.model.response.UserRest;
import com.example.mobileappws.ui.model.response.UserSimpleRest;

@RestController
@RequestMapping("courses")
public class CourseController {

	@Autowired
	CourseService courseService;
	@Autowired
	TimeSlotService timeSlotService;
	
	@PostMapping
	public CourseRest createCourse(@RequestBody CourseRequestModel courseDetails) throws Exception
	{
		CourseRest returnValue=new CourseRest();
		
		if(courseDetails.getCourseName().isEmpty() || courseDetails.getSection()==0
				)
			throw new CourseServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		
		
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT); //it has to match with perfectly matching names

		CourseDto courseDto=modelMapper.map(courseDetails,CourseDto.class);
		
		CourseDto createdCourse=courseService.createCourse(courseDto);
		returnValue=modelMapper.map(createdCourse, CourseRest.class);
		
		
		return returnValue; 
		
	}
	
	@PutMapping()
	public CourseRest updateCorse(@RequestBody CourseRequestModel courseDetails) 
	{
		CourseRest returnValue=new CourseRest();
		
		if(courseDetails.getCourseName().isEmpty()) throw new CourseServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT); //it has to match with perfectly matching names
		
		CourseDto courseDto=modelMapper.map(courseDetails, CourseDto.class);
		
		CourseDto updatedCourse=courseService.updateCourse(courseDto);
		
		returnValue=modelMapper.map(updatedCourse, CourseRest.class);
		
		return returnValue; 
	}
	
	
	@PutMapping(path="/{id}/deleteStudent")
	public CourseRest deleteStudentFromCourse(@RequestBody CourseRequestModel courseDetails,@PathVariable String id) 
	{
		CourseRest returnValue=new CourseRest();
		
		if(courseDetails.getCourseName().isEmpty()) throw new CourseServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT); //it has to match with perfectly matching names
		
		CourseDto courseDto=modelMapper.map(courseDetails, CourseDto.class);
		
		CourseDto updatedCourse=courseService.deleteStudentFromCourse(courseDto,id);
		
		returnValue=modelMapper.map(updatedCourse, CourseRest.class);
		
		return returnValue; 
	}
	@GetMapping(path="/{id}/getParticipants")
	public List<UserSimpleRest> getParticipantsOfTheCourse(@PathVariable String id) 
	{
		List<UserSimpleRest> returnValue=new ArrayList<>();
		
		
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT); //it has to match with perfectly matching names
		
		
		
		List<UserDto> participants=new ArrayList<>();
		participants=courseService.getParticipantsOfTheCourse(id);
		
		for(UserDto participant:participants) {
			returnValue.add(modelMapper.map(participant, UserSimpleRest.class));
		}
		
	
		
		return returnValue; 
	}
		
	
	@GetMapping(path="/{id}/timeslots")
	public List<TimeSlotRest> getCourseTimeSlots(@PathVariable String id) 
	{
		List<TimeSlotRest>  returnValue=new ArrayList();
		
		List<TimeSlotDto> timeSlotsDTO= timeSlotService.getTimeSlots(id);
		
		if(timeSlotsDTO!=null && !timeSlotsDTO.isEmpty()) {
			
			ModelMapper modelMapper=new ModelMapper();
			Type listType=new TypeToken<List<TimeSlotRest>>() {}.getType();
			returnValue=modelMapper.map(timeSlotsDTO,listType);
		}
		
		
		return returnValue; 
	}
	@GetMapping(path = "/{id}/getAnnouncements")
	public List<AnnouncementRest> getAnnouncementOfTheCourse(@PathVariable String id){
		
		List<AnnouncementRest> returnValue = new ArrayList<>();
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT);
		
		List<AnnouncementDto> announcments = new ArrayList<>();
		announcments = courseService.getAnnouncementsOfTheCourse(id);
		
		for(AnnouncementDto announcement:announcments) {
			returnValue.add(modelMapper.map(announcement, AnnouncementRest.class));
		}
		return returnValue;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
