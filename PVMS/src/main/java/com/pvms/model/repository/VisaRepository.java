package com.pvms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pvms.model.pojo.ApplyVisa;
import com.pvms.model.pojo.Registration;

public interface VisaRepository extends JpaRepository<ApplyVisa,String> {
	List<ApplyVisa> findByRegistrationId(Registration registrationId);
}
