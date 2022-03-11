package com.example.mobileappws.io.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.mobileappws.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
	UserEntity findByEmail(String email);
	UserEntity findByUserId(String userId);
	UserEntity findUserByEmailVerificationToken(String token);
	
	@Query(value="select * from Users u where u.EMAIL_VERIFICATION_STATUS='true'",
			countQuery="select * from Users u where u.EMAIL_VERIFICATION_STATUS='true'"
			,nativeQuery=true)
	Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);
	
	@Query(value="select u.* from ((courses c inner join users_courses uc on c.id=uc.course_id) "
			+ " inner join users u on uc.user_id=u.id)"
			+ " where c.course_id=:courseId",nativeQuery = true)
	List<UserEntity> getUsersOfTheCourseByCourseId(@Param("courseId")String courseId);
	
	
	
	
	

	@Query(value="select * from Users u where u.first_name=?1 ",nativeQuery = true)
	List<UserEntity> findUserByFirstName(String firstName);
	
	@Query(value="select * from Users u where u.first_name=:lastName ",nativeQuery = true)
	List<UserEntity> findUserByLastName(@Param("lastName")String lastName);

	@Query(value="select * from Users u where u.first_name LIKE %:keyword%",nativeQuery = true)
	List<UserEntity> findUsersByKeyword(@Param("keyword")String keyword);

	@Query(value="select u.first_name,u.last_name from Users u where u.first_name LIKE %:keyword%",nativeQuery = true)
	List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword")String keyword);

	@Transactional
	@Modifying
	@Query(value="update users u set u.EMAIL_VERIFICATION_STATUS=:emailVerificationStatus where u.user_id=:userId",nativeQuery = true)
	void updateUserEmailVerificationStatus(@Param("emailVerificationStatus")boolean emailVerificationStatus,
			@Param("userId")String userId);
}
