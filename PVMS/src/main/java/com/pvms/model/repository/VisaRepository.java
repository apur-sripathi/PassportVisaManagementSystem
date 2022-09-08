package com.pvms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pvms.model.pojo.ApplyPassport;
import com.pvms.model.pojo.ApplyVisa;
import com.pvms.model.pojo.Registration;

public interface VisaRepository extends JpaRepository<ApplyVisa,String> {
	//fetches all the visa records based on registration id of the user.
	List<ApplyVisa> findByRegistrationId(Registration registrationId);
	//fetches visa records based on passport id of the user.
	List<ApplyVisa> findByPassId(ApplyPassport passId);
}
