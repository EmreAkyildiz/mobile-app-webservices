package com.example.mobileappws;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.mobileappws.io.entity.AuthorityEntity;
import com.example.mobileappws.io.entity.RoleEntity;
import com.example.mobileappws.io.entity.UserEntity;
import com.example.mobileappws.io.repository.AuthorityRepository;
import com.example.mobileappws.io.repository.RoleRepository;
import com.example.mobileappws.io.repository.UserRepository;
import com.example.mobileappws.shared.dto.Roles;
import com.example.mobileappws.shared.dto.Utils;

@Component
public class InitialUsersSetup {

	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCyrptPasswordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	
	@EventListener
	@Transactional
	public void onApplicationEvent(ApplicationReadyEvent event) {
		System.out.println("denemstarttttttt");
		
		AuthorityEntity readAuthority=createAuthority("READ_AUTHORITY");
		AuthorityEntity writeAuthority=createAuthority("WRITE_AUTHORITY");
		AuthorityEntity deleteAuthority=createAuthority("DELETE_AUTHORITY");
	
		RoleEntity roleStudent=createRole(Roles.ROLE_STUDENT.name(), Arrays.asList(readAuthority,writeAuthority));
		RoleEntity roleAcademician=createRole(Roles.ROLE_ACADEMICIAN.name(), Arrays.asList(readAuthority,writeAuthority,deleteAuthority));
		
		if(roleAcademician==null) return;
		
		if(userRepository.findByEmail("fortest@test.com")!=null) return;
		UserEntity adminUser=new UserEntity();
		adminUser.setFirstName("batuhan");
		adminUser.setLastName("aydoğdu");
		adminUser.setEmail("fortest@test.com");
		adminUser.setEmailVerificationStatus(true);
		adminUser.setUserId(utils.generateUserId(30));
		adminUser.setEncryptedPassword(bCyrptPasswordEncoder.encode("123"));
		adminUser.setRoles(Arrays.asList(roleAcademician,roleStudent));
		
		userRepository.save(adminUser);
		
	}
	
	@Transactional
	private AuthorityEntity createAuthority(String name) {
		AuthorityEntity authority = authorityRepository.findByName(name);
		if(authority==null) {
			authority= new AuthorityEntity(name);
			authorityRepository.save(authority);
		}
		return authority;
	}
	
	@Transactional
	private RoleEntity createRole(
			String name,
			Collection<AuthorityEntity> authorities) {
		
		
		RoleEntity role = roleRepository.findByName(name);
		if(role==null) {
			role= new RoleEntity(name);
			role.setAuthorities(authorities);
			roleRepository.save(role);
		}
		return role;
	}
	
	
}
