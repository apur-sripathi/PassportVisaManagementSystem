package com.pvms.model.dao.service;

import java.time.LocalDate;
import java.util.List;

import com.pvms.model.pojo.ApplyVisa;
import com.pvms.model.pojo.Registration;

public interface VisaService {
	String insertVisaDetails(ApplyVisa applyVisa);
	List<ApplyVisa> getVisaDetails();
	
	Double CalculateVisaCancelCharges(String visaId,LocalDate doc);
	String cancelVisa(ApplyVisa visa);
	List<ApplyVisa> getByRegId(String regId);
}
