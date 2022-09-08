package com.pvms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pvms.model.pojo.Registration;

public interface RegistrationRepository extends JpaRepository<Registration,String> {
	//Based on registration id this method fetches contact number from database.
	Registration findContactById(String id);
	//gets list of user details based on users apply type.
	List<Registration> findUsersByApplyType(Integer applyType);
	//gets list of user details based on users email.
	List<Registration> findUsersByEmail(String email);
}
