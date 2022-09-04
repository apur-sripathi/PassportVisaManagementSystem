package com.pvms.model.dao.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvms.model.dao.service.PassportService;
import com.pvms.model.pojo.ApplyPassport;
import com.pvms.model.pojo.City;
import com.pvms.model.pojo.Registration;
import com.pvms.model.pojo.State;
import com.pvms.model.repository.CityRepository;
import com.pvms.model.repository.PassportRepository;
import com.pvms.model.repository.RegistrationRepository;
import com.pvms.model.repository.StateRepository;

@Service
public class PassportServiceImpl implements PassportService {
	
	@Autowired
	PassportRepository passportRepository;
	
	@Autowired
	RegistrationRepository registrationRepository;
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	public String generateUserId(int bookletType){
		String str=bookletType==1?"30":"60";
		
	    List<ApplyPassport> list= passportRepository.findAll();   
	    if(!list.isEmpty()) {
	    	Integer i = list.stream().map(d->Integer.parseInt(d.getPassId().split("-")[1].substring(1))).reduce(0,(a,b)->Integer.max(a,b))+1;
		   return "FPS-"+str+i;  
	    }
	    return "FPS-"+str+"1000";
	}
	
	public Integer calculateCost(Integer typeOfService) {
		return typeOfService==1?2500:5000;
	}
	
	public Integer calculateRenewalCost(Integer typeOfService) {
		return typeOfService==1?1500:3000;
	}
	
	@Override
	public String insertPassportDetails(ApplyPassport applyPassport)
	{
		LocalDate now = LocalDate.now();
		Integer t = applyPassport.getTypeOfService();
		Integer bt = applyPassport.getBookletType();
		Registration x = registrationRepository.findById(applyPassport.getRegistrationIdt()).orElseGet(()->null);
	  	if(x==null) return "Enter valid Id";
	  	List<ApplyPassport> y = passportRepository.findByRegistrationId(x);
	  	List<ApplyPassport> man = passportRepository.findAll();
		if(!y.isEmpty() && y.get(y.size()-1).getExpiryDate().isAfter(now)) return "You already have a passport with id : "+y.get(y.size()-1).getPassId();
//		Integer bw = man.stream().map(d->Integer.parseInt(d.getPassId().split("-")[1])).reduce(0,(a,b)->Integer.max(a,b));
//		String find = "FPS-"+bw;
//		ApplyPassport xfind = passportRepository.findById(find).get();
		
		applyPassport.setPassId(generateUserId(bt));
		applyPassport.setExpiryDate(applyPassport.getIssueDate().plusYears(10));
	  	
	  	applyPassport.setRegistrationId(x);
	  	applyPassport.setCityId(cityRepository.findById(applyPassport.getCityIdt()).orElseGet(()->null));
	  	applyPassport.setStateId(stateRepository.findById(applyPassport.getStateIdt()).orElseGet(()->null));
		passportRepository.save(applyPassport);
		return "'"+applyPassport.getPassId()+"'"+". Passport application cost is Rs."+calculateCost(t);
	}

	@Override
	public List<ApplyPassport> getAllPassports() {
		return passportRepository.findAll();
	}

	@Override
	public String reissuePassport(ApplyPassport applyPassport) {
		LocalDate now = LocalDate.now();
		Registration x = registrationRepository.findById(applyPassport.getRegistrationIdt()).orElseGet(()->null);
		List<ApplyPassport> y = passportRepository.findByRegistrationId(x);
		if(y.isEmpty()) return "You need to Apply to reissue a passport";
		//if(y.get(y.size()-1).getExpiryDate().isBefore(applyPassport.getIssueDate())) return "";
		
		if(!y.isEmpty() && y.get(y.size()-1).getExpiryDate().isAfter(applyPassport.getIssueDate())) {
			y.get(y.size()-1).setExpiryDate(applyPassport.getIssueDate());
		}
		
		
		passportRepository.save(y.get(y.size()-1));
		applyPassport.setPassId(generateUserId(applyPassport.getBookletType()));
		applyPassport.setExpiryDate(applyPassport.getIssueDate().plusYears(10));
	  	
	  	applyPassport.setRegistrationId(x);
	  	applyPassport.setCityId(cityRepository.findById(applyPassport.getCityIdt()).orElseGet(()->null));
	  	applyPassport.setStateId(stateRepository.findById(applyPassport.getStateIdt()).orElseGet(()->null));
		passportRepository.save(applyPassport);
		return "Passport reissue is successfully done.\nAmount to be paid is "+calculateRenewalCost(applyPassport.getTypeOfService())+
				".\n Passport issue date is "+applyPassport.getIssueDate()+" and expiry date is "+applyPassport.getIssueDate().plusYears(10)+".";
	}

	@Override
	public List<ApplyPassport> getPassportByRegistrationId(String registrationId) {
		Registration R = registrationRepository.findById(registrationId).orElseGet(()->null);
		return passportRepository.findByRegistrationId(R);
	}

	@Override
	public ApplyPassport getRecenetPassport(String registrationId) {
		Registration R = registrationRepository.findById(registrationId).orElseGet(()->null);
		try {
			return passportRepository.findByRegistrationId(R).stream().filter(i->i.getExpiryDate().isAfter(LocalDate.now())).collect(Collectors.toList()).get(0);
		}catch (Exception e) {
			return null;
		}
	}
	
	
	
	 
}
