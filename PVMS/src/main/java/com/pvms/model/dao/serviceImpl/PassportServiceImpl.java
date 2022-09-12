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
	
	//generates the user id based on the booklet type.
	public String generateUserId(int bookletType){
		String str=bookletType==1?"30":"60";
	    List<ApplyPassport> list= passportRepository.findAll();   
	    if(!list.isEmpty()) {
	    	Integer i = list.stream().map(d->Integer.parseInt(d.getPassId().split("-")[1].substring(1))).reduce(0,(a,b)->Integer.max(a,b))+1;
		   return "FPS-"+str+i;  
	    }
	    return "FPS-"+str+"1000";
	}
	
	//calculates the cost based on the type of service
	public Integer calculateCost(Integer typeOfService) {
		return typeOfService==1?2500:5000;
	}
	
	//calculates the renewal cost based on the type of service
	public Integer calculateRenewalCost(Integer typeOfService) {
		return typeOfService==1?1500:3000;
	}
	
	//Insertion of passport details into database.
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
	  	
	  	//fetches the id of the last passport record of the user
	  	Integer large = y.stream().map(xy->Integer.parseInt(xy.getPassId().split("-")[1].substring(1))).reduce(0,(a,b)->Integer.max(a,b));
	  	//it checks for the expiry of the passport and if passport is not expired will return the passport deyails.
	  	if(!y.isEmpty() && !y.stream().filter(xy->xy.getExpiryDate().isAfter(now)&&xy.getPassId().contains(Integer.toString(large))).collect(Collectors.toList()).isEmpty()) return "You already have a passport with id : "+y.get(y.size()-1).getPassId();
		applyPassport.setPassId(generateUserId(bt));
		applyPassport.setExpiryDate(applyPassport.getIssueDate().plusYears(10));
	  	
	  	applyPassport.setRegistrationId(x);
	  	applyPassport.setCityId(cityRepository.findById(applyPassport.getCityIdt()).orElseGet(()->null));
	  	applyPassport.setStateId(stateRepository.findById(applyPassport.getStateIdt()).orElseGet(()->null));
		passportRepository.save(applyPassport);
		//after successful completion of all the fields object is stored in database and message is returned to the user.
		return "'"+applyPassport.getPassId()+"'"+". Passport application cost is Rs."+calculateCost(t);
	}

	//fetches all the passport records.
	@Override
	public List<ApplyPassport> getAllPassports() {
		return passportRepository.findAll();
	}

	//this method is for reissue of passport.
	@Override
	public String reissuePassport(ApplyPassport applyPassport) {
		LocalDate now = LocalDate.now();
		Registration x = registrationRepository.findById(applyPassport.getRegistrationIdt()).orElseGet(()->null);
		List<ApplyPassport> y = passportRepository.findByRegistrationId(x);
		if(y.isEmpty()) return "You need to Apply to reissue a passport";
	
		//fetches the last record pass id sequence.
		Integer large = y.stream().map(xy->Integer.parseInt(xy.getPassId().split("-")[1].substring(1))).reduce(0,(a,b)->Integer.max(a,b));
		
		//fetches the record with the sequence.
		List<ApplyPassport> yy = y.stream().filter(xn->xn.getPassId().contains(Integer.toString(large))).collect(Collectors.toList());
		
		//last passport records expiry date is set to new passport issue date.
		if(!y.isEmpty() && yy.get(0).getExpiryDate().isAfter(applyPassport.getIssueDate())) {
			yy.get(0).setExpiryDate(LocalDate.now().minusDays(1));
		}
		
		//saved to passport repository.
		passportRepository.save(yy.get(0));
		applyPassport.setPassId(generateUserId(applyPassport.getBookletType()));
		applyPassport.setExpiryDate(applyPassport.getIssueDate().plusYears(10));
	  	
	  	applyPassport.setRegistrationId(x);
	  	applyPassport.setCityId(cityRepository.findById(applyPassport.getCityIdt()).orElseGet(()->null));
	  	applyPassport.setStateId(stateRepository.findById(applyPassport.getStateIdt()).orElseGet(()->null));
		passportRepository.save(applyPassport);
		//returns the message after successful insertion of the passport.
		return "Passport reissue is successfully done.\nAmount to be paid is "+calculateRenewalCost(applyPassport.getTypeOfService())+
				".\n Passport issue date is "+applyPassport.getIssueDate()+" and expiry date is "+applyPassport.getIssueDate().plusYears(10)+"."+
				"Your New Passport Id is : '"+applyPassport.getPassId()+"'"+".";
	}

	//gets all passport records based on registration id.
	@Override
	public List<ApplyPassport> getPassportByRegistrationId(String registrationId) {
		Registration R = registrationRepository.findById(registrationId).orElseGet(()->null);
		return passportRepository.findByRegistrationId(R);
	}

	//its fetches the recent passport details.
	@Override
	public ApplyPassport getRecenetPassport(String registrationId) {
		Registration R = registrationRepository.findById(registrationId).orElseGet(()->null);
		try {
			//fetches the passport details for the user based on expiry date of the passport.
			return passportRepository.findByRegistrationId(R).stream().filter(i->i.getExpiryDate().isAfter(LocalDate.now())).collect(Collectors.toList()).get(0);
		}catch (Exception e) {
			return null;
		}
	}		 
}
