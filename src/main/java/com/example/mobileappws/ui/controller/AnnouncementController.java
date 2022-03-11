package com.example.mobileappws.ui.controller;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobileappws.io.entity.CourseEntity;
import com.example.mobileappws.service.AnnouncementService;
import com.example.mobileappws.service.CourseService;
import com.example.mobileappws.shared.dto.AnnouncementDto;
import com.example.mobileappws.shared.dto.CourseDto;
import com.example.mobileappws.shared.dto.UserDto;
import com.example.mobileappws.ui.model.request.AnnouncementRequestModel;
import com.example.mobileappws.ui.model.request.UserAnnouncementRequestModel;
import com.example.mobileappws.ui.model.response.AnnouncementRest;
import com.example.mobileappws.ui.model.response.CourseRest;
import com.example.mobileappws.ui.model.response.ErrorMessages;

@RestController
@RequestMapping("announcements")
public class AnnouncementController {
	
	@Autowired
	AnnouncementService announcementService;
	@Autowired
	CourseService courseService;
	
	
	@PostMapping("/courses/{courseName}")
	public AnnouncementRest createAnnouncement(@PathVariable(value="courseName")String courseName, @RequestBody AnnouncementRequestModel announcementDetails) throws Exception
	{
		AnnouncementRest returnValue = new AnnouncementRest();
		
		
		
	//	if(announcementDetails.getContent().isEmpty()) {
	//		throw new AnnouncementServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
	//	}
		
		ModelMapper modelMapper=new ModelMapper();
		
		
		AnnouncementDto announcementDto = modelMapper.map(announcementDetails, AnnouncementDto.class);
		
		
		
		AnnouncementDto createdAnnouncement = announcementService.createAnnouncement(announcementDto,courseName,announcementDetails.getEmail());
			
		returnValue=modelMapper.map(createdAnnouncement, AnnouncementRest.class);
		
		
		return returnValue;
		
	}

}
