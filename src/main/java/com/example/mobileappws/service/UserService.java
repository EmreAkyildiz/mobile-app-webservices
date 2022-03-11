package com.example.mobileappws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.mobileappws.shared.dto.UserDto;

public interface UserService extends UserDetailsService{

	UserDto createUser(UserDto user);
	UserDto getUser(String email);
	UserDto getUserByUserId(String userId);
	UserDto updateUser(String userId, UserDto user);
	void deleteUser(String userId);
	List<UserDto> getUsers(int page,int limit);
	boolean verifyEmailToken(String token);
}
