package com.example.mobileappws.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mobileappws.exceptions.UserServiceException;
import com.example.mobileappws.io.entity.RoleEntity;
import com.example.mobileappws.io.entity.UserEntity;
import com.example.mobileappws.io.repository.RoleRepository;
import com.example.mobileappws.io.repository.UserRepository;
import com.example.mobileappws.security.UserPrincipal;
import com.example.mobileappws.service.UserService;
import com.example.mobileappws.shared.dto.AddressDTO;
import com.example.mobileappws.shared.dto.*;
import com.example.mobileappws.shared.dto.Utils;
import com.example.mobileappws.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public UserDto createUser(UserDto user) {
		
		//UserEntity deneme=userRepository.findByEmail(user.getEmail());
		if(userRepository.findByEmail(user.getEmail())!=null)
			{
			throw new RuntimeException("Record already exists.");
			}
		
		for(int i=0;i<user.getAddresses().size();i++) {
			AddressDTO address =user.getAddresses().get(i);
			address.setUserDetails(user);
			address.setAddressId(utils.generateAddressId(30));
			user.getAddresses().set(i, address);
		}
		
		//create role based on email
		
		if(user.getEmail().substring(user.getEmail().length()-12, user.getEmail().length()).equals("@isik.edu.tr")) {
			//@isik.edu.tr
			user.getRoles().add(Roles.ROLE_STUDENT.name());
			
		}
		else if(user.getEmail().substring(user.getEmail().length()-14, user.getEmail().length()).equals("@isikun.edu.tr")) {
			//@isikun.edu.tr
			user.getRoles().add(Roles.ROLE_ACADEMICIAN.name());
			
		}
		else {
			
			throw new RuntimeException("Only Isik University student can register.");
			
			
		}
		
		
		
		
		
		
		//UserEntity userEntity=new UserEntity();
		//BeanUtils.copyProperties(user, userEntity);
		ModelMapper modelMapper=new ModelMapper();
		UserEntity userEntity=modelMapper.map(user, UserEntity.class);
		
		
		
		
		String publicUserId=utils.generateUserId(30);
		userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setUserId(publicUserId);
		
		
		userEntity.setEmailVerificationStatus(false);
		
		
		//set roles
		Collection <RoleEntity> roleEntities=new HashSet<>();
		for(String role: user.getRoles()) {
			RoleEntity roleEntity=roleRepository.findByName(role);
			if(roleEntity!=null) {
				roleEntities.add(roleEntity);
			}
			
		}
		userEntity.setRoles(roleEntities);
		
		UserEntity storedUserDetails= userRepository.save(userEntity);
		
		//UserDto returnValue=new UserDto();
		//BeanUtils.copyProperties(storedUserDetails, returnValue);
		UserDto returnValue=modelMapper.map(storedUserDetails, UserDto.class);
		//Send email
		//new AmazonSES().verifyEmail(returnValue);
		
		
		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity=userRepository.findByEmail(email);
		if(userEntity==null) throw new UsernameNotFoundException(email);
		
		return new UserPrincipal(userEntity);
		
	//	return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
	//			true,
	//			true,true,
	//			true
	//			,new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity=userRepository.findByEmail(email);
		if(userEntity==null) throw new UsernameNotFoundException(email);
		
		UserDto returnValue=new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
		
		
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserDto returnValue=new UserDto();
		UserEntity userEntity=userRepository.findByUserId(userId);
		if(userEntity==null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		BeanUtils.copyProperties(userEntity, returnValue);
		
		
		
		return returnValue;
	}

	@Override
	public UserDto updateUser(String userId, UserDto user) {
		UserDto returnValue=new UserDto();
		UserEntity userEntity=userRepository.findByUserId(userId);
		
		if(userEntity==null) 
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		
		//if null don't update**
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		
		UserEntity updatedUserDetails=userRepository.save(userEntity);
		
		BeanUtils.copyProperties(updatedUserDetails, returnValue);
		return returnValue;
	}

	@Override
	public void deleteUser(String userId) {
UserEntity userEntity=userRepository.findByUserId(userId);
		
		if(userEntity==null) 
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		List<UserDto> returnValue= new ArrayList<>();
		
		if(page>0) page=page-1;
		
		Pageable pageableRequest=PageRequest.of(page, limit);
		Page<UserEntity> usersPage=userRepository.findAll(pageableRequest);
		List<UserEntity> users=usersPage.getContent();
		
		for(UserEntity userEntity:users) {
			UserDto userDto=new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		
		
		
		return returnValue;
		
	}
	@Override
	public boolean verifyEmailToken(String token) {
		boolean returnValue = false;
		// Find user by token
		UserEntity userEntity = userRepository.findUserByEmailVerificationToken(token);
		if (userEntity != null) {
		   boolean hastokenExpired = Utils.hasTokenExpired(token);
		    if (!hastokenExpired) {
		        userEntity.setEmailVerificationToken(null);
		        userEntity.setEmailVerificationStatus(Boolean.TRUE);
		        userRepository.save(userEntity);
		        returnValue = true;
		}
		
		}
		return returnValue;
	}

}
