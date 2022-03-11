package com.example.mobileappws.io.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.mobileappws.io.entity.AssignmentEntity;

@Repository
public interface AssignmentRepository extends CrudRepository<AssignmentEntity, Long> {

}
