package com.pvms.model.dao.service;

import java.util.List;

import com.pvms.model.pojo.ApplyPassport;
import com.pvms.model.pojo.Registration;

public interface PassportService {
	String insertPassportDetails(ApplyPassport applyPassport);
	List<ApplyPassport> getAllPassports();
	String reissuePassport(ApplyPassport applyPassport);
	List<ApplyPassport> getPassportByRegistrationId(String registrationId);
	ApplyPassport getRecenetPassport(String registrationId);
}
