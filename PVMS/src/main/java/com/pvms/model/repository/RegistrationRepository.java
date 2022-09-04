package com.pvms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pvms.model.pojo.Registration;

public interface RegistrationRepository extends JpaRepository<Registration,String> {
	Registration findTopByOrderByIdDesc();
	Registration findContactById(String id);
	List<Registration> findUsersByApplyType(Integer applyType);
	List<Registration> findUsersByEmail(String email);
}
