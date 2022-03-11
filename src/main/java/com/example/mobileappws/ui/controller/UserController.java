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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mobileappws.exceptions.UserServiceException;
import com.example.mobileappws.service.AddressService;
import com.example.mobileappws.service.CourseService;
import com.example.mobileappws.service.UserService;
import com.example.mobileappws.shared.dto.AddressDTO;
import com.example.mobileappws.shared.dto.CourseDto;
import com.example.mobileappws.shared.dto.Roles;
import com.example.mobileappws.shared.dto.UserDto;
import com.example.mobileappws.ui.model.request.UserDetailsRequestModel;
import com.example.mobileappws.ui.model.response.AddressesRest;
import com.example.mobileappws.ui.model.response.CourseRest;
import com.example.mobileappws.ui.model.response.CourseSimpleRest;
import com.example.mobileappws.ui.model.response.ErrorMessages;
import com.example.mobileappws.ui.model.response.OperationStatusModel;
import com.example.mobileappws.ui.model.response.RequestOperationName;
import com.example.mobileappws.ui.model.response.RequestOperationStatus;
import com.example.mobileappws.ui.model.response.UserRest;
import com.example.mobileappws.ui.model.response.UserSimpleRest;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	AddressService addressService;
	@Autowired
	CourseService courseService;
	
	
	//@PostAuthorize("hasRole('ACADEMICIAN') or returnObject.userId==principal.userId")
	@GetMapping(path="/{id}")
	public UserRest getUser(@PathVariable String id) 
	{
		UserRest returnValue=new UserRest();
		UserDto userDto= userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnValue);
		
		return returnValue; 
	}
	
//  "/" ile koyulanlar pathvariable ama ? ile gelenler query string requeestparam
	@GetMapping
	public List<UserRest> getUsers(@RequestParam(value="page",defaultValue="0") int page,
			@RequestParam(value="limit",defaultValue="25") int limit){
		List<UserRest> returnValue=new ArrayList<>();
		List<UserDto> users=userService.getUsers(page,limit);	
		for(UserDto userDto:users) {
			UserRest userModel=new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}
		
		
		return returnValue;
	}
	
	
	
	@PostMapping
	public UserSimpleRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception
	{
		UserSimpleRest returnValue=new UserSimpleRest();
		
		if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		
		//UserDto userDto=new UserDto();
		//BeanUtils.copyProperties(userDetails, userDto);
		
		ModelMapper modelMapper=new ModelMapper();
		UserDto userDto=modelMapper.map(userDetails,UserDto.class);
		userDto.setRoles(new HashSet<>(Arrays.asList(Roles.ROLE_STUDENT.name())));
		//TODO here I set student role to everyone but the email which include @isikun.edu.tr is academician
		//who has @isik.edu.tr is student this have to be managed with the frontend team
		
		
		UserDto createdUser=userService.createUser(userDto);
		returnValue=modelMapper.map(createdUser, UserSimpleRest.class);
		                           
		
		//BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue; 
		
	}
	
	//@PreAuthorize("hasRole('ROLE_ACADEMICIAN') or #id==principal.userId") kullanıcı kendi hesabını siler veya akademisyen siler
	@DeleteMapping(path="/{id}")
	public OperationStatusModel deleteUser(@PathVariable String id) 
	{
		OperationStatusModel returnValue=new OperationStatusModel();
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		returnValue.setOperationResult(RequestOperationStatus.SUCCES.name());
		
		userService.deleteUser(id);
		
		return returnValue;
	}
	
	@PutMapping(path="/{id}")
	public UserRest updateUser(@RequestBody UserDetailsRequestModel userDetails,@PathVariable String id) 
	{
		UserRest returnValue=new UserRest();
		
		if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		
		UserDto userDto=new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto updatedUser=userService.updateUser(id,userDto);
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		return returnValue; 
	}
	
	//localhost:8080/mobile-app-ws/users/jdfjfsfqwe/addresses
	@GetMapping(path="/{id}/addresses")
	public List<AddressesRest> getUserAddresses(@PathVariable String id) 
	{
		List<AddressesRest>  returnValue=new ArrayList();
		
		List<AddressDTO> addressesDTO= addressService.getAddresses(id);
		
		if(addressesDTO!=null && !addressesDTO.isEmpty()) {
			
			ModelMapper modelMapper=new ModelMapper();
			Type listType=new TypeToken<List<AddressesRest>>() {}.getType();
			returnValue=modelMapper.map(addressesDTO,listType);
		}
		
		
		return returnValue; 
	}
	
	@GetMapping(path="/{userId}/addresses/{addressId}")
	public AddressesRest getUserAddress(@PathVariable String addressId) 
	{
		
		
		AddressDTO addressesDTO= addressService.getAddress(addressId);
		
		ModelMapper modelMapper=new ModelMapper();

		return modelMapper.map(addressesDTO, AddressesRest.class); 
	}
	
	//@CrossOrigin(origins="*") veya belirli port
	
	
	@PreAuthorize("hasRole('ACADEMICIAN') or #userId==principal.userId")
	@GetMapping(path="/{userId}/courses")
	public List<CourseSimpleRest> getMyCourses(@PathVariable String userId) 
	{
		List<CourseSimpleRest> returnValue=new ArrayList<>();
		
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		List<CourseDto> updatedCourses=courseService.getMyCourses(userId);

		
		for(CourseDto courseDto:updatedCourses) {
			
			returnValue.add(modelMapper.map(courseDto, CourseSimpleRest.class));
				
		}	
		return returnValue; 
	}
	/*
	 * http://localhost:8080/mobile-app-ws/users/email-verification?token=sdfsdf
	 */
	
	@GetMapping(path = "/email-verification")
	public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {
    OperationStatusModel returnValue = new OperationStatusModel();
    returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
    boolean isVerified = userService.verifyEmailToken(token);
    
    if(isVerified)
    {
       returnValue.setOperationResult(RequestOperationStatus.SUCCES.name());
     } else {
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
    }
    return returnValue;
}

	
}
