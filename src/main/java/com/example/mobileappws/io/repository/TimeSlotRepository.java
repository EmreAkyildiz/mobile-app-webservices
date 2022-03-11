package com.example.mobileappws.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mobileappws.io.entity.CourseEntity;
import com.example.mobileappws.io.entity.TimeSlotEntity;

@Repository
public interface TimeSlotRepository extends CrudRepository<TimeSlotEntity, Long> {

	
	@Query(value="select * from timeslots ts where ts.day=:day and ts.slot=:slot ",nativeQuery = true)
	TimeSlotEntity findTimeSlotByDayAndSlot(@Param("day")String day,@Param("slot")int slot);
	
	@Query(value="select ts.* from timeslots ts inner join courses_timeslots cts on ts.id=cts.timeslot_id where cts.course_id=:courseId",nativeQuery = true)
	List<TimeSlotEntity> getTimeSlotsByCourseId(@Param("courseId")Long courseId);
	
	
	
}
