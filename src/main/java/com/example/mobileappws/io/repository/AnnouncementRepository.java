package com.example.mobileappws.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mobileappws.io.entity.AnnouncementEntity;
@Repository
public interface AnnouncementRepository extends CrudRepository<AnnouncementEntity, Long> {
	AnnouncementEntity findByAnnouncementId(String AnnouncementId);
	
	@Query(value="select * from announcements u where u.courses_id=courses_id ",nativeQuery = true)
	List<AnnouncementEntity> getAnnoucementsOfTheCourseByCourseId(@Param("courses_id")String courseId);
	

}
