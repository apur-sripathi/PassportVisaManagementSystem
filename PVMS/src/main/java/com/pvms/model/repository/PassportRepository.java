package com.pvms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pvms.model.pojo.ApplyPassport;
import com.pvms.model.pojo.Registration;

public interface PassportRepository extends JpaRepository<ApplyPassport, String>{
	////gets list of passport details based on users Registration Id.
	List<ApplyPassport> findByRegistrationId(Registration registrationId);
}
