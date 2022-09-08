package com.pvms.model.dao.serviceImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvms.model.dao.service.VisaService;
import com.pvms.model.pojo.ApplyPassport;
import com.pvms.model.pojo.ApplyVisa;
import com.pvms.model.pojo.Registration;
import com.pvms.model.repository.PassportRepository;
import com.pvms.model.repository.RegistrationRepository;
import com.pvms.model.repository.VisaRepository;

@Service
public class VisaServiceImpl implements VisaService {

	@Autowired
	PassportRepository passportRepository;
	
	@Autowired
	RegistrationRepository registrationRepository;
	
	@Autowired
	VisaRepository visaRepository;
	
	//Used map to get visa cost for the user based on country and user occupation.
	//1,2,3 for countries USA,CHINA,JAPAN respectively and 0,1,2,3,4 indices for STUDENT,PRIVATE EMPLOYEE,GOVT EMPLOYEE,SELF EMPLOYEE,RETIRED EMPLOYEE respectively.
	//based on country we will get a list and based on occupation we can get index and finally we can get cost.
	private static Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>() {{
		put(1, Arrays.asList(2500,2000,4000,6000,1500));
		put(2, Arrays.asList(1500,2000,3000,4000,2000));
    	put(3, Arrays.asList(3500,40000,4500,9000,1000));
		
	}};
	
	//this method is used for insertion of visa details.
	@Override
	public String insertVisaDetails(ApplyVisa applyVisa) {
		
		Registration R = registrationRepository.findById(applyVisa.getRegId()).orElseGet(()->null);
		if(R==null) return "You need to register first";
		if(!R.getId().equals(applyVisa.getRegId())) return "Registration id should be login id";
		applyVisa.setRegistrationId(R);
		ApplyPassport a = passportRepository.findById(applyVisa.getPassid()).orElseGet(()->null);
		if(a==null) return "You need a passport";
		if(a.getIssueDate().isAfter(applyVisa.getDateOfApplication())) return "Passport Issue Date is after the Application Date. So choose correct Date and Passport Issue Date is "+a.getIssueDate();
		if(a.getExpiryDate().isBefore(applyVisa.getDateOfApplication())) return "Passport Expired!!";
		//calculates cost and assigns it to cost variable.
		Integer cost = calculateCost(applyVisa.getOccupation(),applyVisa.getCountry());
		//calculate permit years and assigns to the variable.
		double permitYears = permitYears(applyVisa.getOccupation());
		applyVisa.setPassId(a);
		//gets the passport expiry date.
		LocalDate x = applyVisa.getPassId().getExpiryDate();
		//gets the visa expiry date.
		LocalDate y = applyVisa.getDateOfApplication().plusDays(10).plusYears((long) permitYears);
		//if passport expiry date is before visa expiry date then visa expiry date is passport expiry date.
		applyVisa.setDateOfExpiry(y.isAfter(x)?x:y);
		List<ApplyVisa> list = visaRepository.findAll();
		if(!list.isEmpty()) {
			Integer mx = visaRepository.findAll().stream().map(mj->Integer.parseInt(mj.getVisaId())).reduce(0,(ax,b)->Integer.max(ax, b))+1;
			applyVisa.setVisaId(Integer.toString(mx));
		}else {
			applyVisa.setVisaId("1000");
		}
		applyVisa.setStatus("active");
		visaRepository.save(applyVisa);
		//upon successful insertion of visa details message will be displayed to the user.
		return "Dear User your Visa request has been accepted successfully with id"+
		applyVisa.getVisaId()+", User Id "+applyVisa.getRegId()+". Destination "+
		getCountry(applyVisa.getCountry())+", Occupation "+getOccupation(applyVisa.getOccupation())+", Date of Application"+applyVisa.getDateOfApplication()
		+"Date Of Issue"+applyVisa.getDateOfApplication().plusDays(10)+"Date of Expiry"+applyVisa.getDateOfExpiry()+" Registration Cost "
		+calculateCost(applyVisa.getOccupation(),applyVisa.getCountry());  
	}
	
	//calculates cost based on occupation and place.
	public Integer calculateCost(Integer occupation,Integer place) {
		return map.get(place).get(occupation-1);
	}
	
	//gets country using its integer value.
	public String getCountry(Integer i) {
		return i==1?"USA":i==2?"CHINA":"JAPAN";
	}
	
	//gets occupation using its integer value.
	public String getOccupation(Integer i) {
		return i==1?"Student":i==2?"Private Employee ":i==3?"Government Employee":i==4?"Self Employeed":"Retired Eemployee";
	}
	
	//fetches permit years based on occupation.
	public double permitYears(Integer o) {
		return o==1?2:o==2?3:o==3?4:o==4?1:1.5;
	}

	//fetches all the visa details.
	@Override
	public List<ApplyVisa> getVisaDetails() {
		return visaRepository.findAll();
	}

	//calculates the visa cancellation charges.
	//Occupations : 1,2,3,4,5 are for STUDENT,PRIVATE EMPLOYEE,GOVT EMPLOYEE,SELF EMPLOYEE,RETIRED EMPLOYEE respectively.
	@Override
	public Double CalculateVisaCancelCharges(String visaId, LocalDate doc) {

		ApplyVisa applyVisa=visaRepository.findById(visaId).orElseGet(()->null);
		LocalDate dt=applyVisa.getDateOfExpiry();	
        Period p=Period.between(doc, dt);
        int Diff_mon=p.getMonths()+(p.getYears()*12);
        if(applyVisa.getOccupation()==1 && Diff_mon<6){
        	 return 1000.0;
         }
         else if(applyVisa.getOccupation()==1 && Diff_mon>=6) {
        	 return 1500.0;
         }
         else if(applyVisa.getOccupation()==2 && Diff_mon < 6 ){
        	 return 1750.0;
         }
         else  if(applyVisa.getOccupation()==2 && Diff_mon >=6 && Diff_mon <12 ){
        	 return 1850.0;
         }
         else if(applyVisa.getOccupation()==2 && Diff_mon >=12){
        	 return 2000.0;
        }
         else if(applyVisa.getOccupation()==3 && Diff_mon < 6 ){
        	 return 2050.0;
         }
         else if(applyVisa.getOccupation()==3 && Diff_mon >=6 && Diff_mon <12 ){
        	 return 2100.0;
         }
         else  if(applyVisa.getOccupation()==3 && Diff_mon >=12) {
        	 return 2400.0;
         }
         else if(applyVisa.getOccupation()==4 && Diff_mon<6){
        	 return 1500.0;
         }
         else  if(applyVisa.getOccupation()==4 && Diff_mon>=6){
        	 return 1700.0;
         }
         else  if(applyVisa.getOccupation()==5 && Diff_mon<6){
        	 return 750.0;
         }
         else {
        	 return 1100.0;
         }  
	}

	//this method is for cancellation of visa.
	@Override
	public String cancelVisa(ApplyVisa visa) {
		//gets passport details based on passport entered by user.
		ApplyPassport passport = passportRepository.findById(visa.getPassid()).get();
		//gets visa details based on passport of the user.
		List<ApplyVisa> visaList = visaRepository.findByPassId(passport);
		//if list is empty we returns with a message.
		if(visaList.isEmpty()) return "There does not exist any Visa for the given Passport id : "+visa.getPassid();
		//filters the list based up on status="active";
		visaList = visaList.stream().filter(mn->mn.getStatus().equals("active")).collect(Collectors.toList());
		if(visaList.isEmpty()) return "There is no active Visa";
		//fetches the single record based on visa number enterd by the user.
		visaList = visaList.stream().filter(xy->xy.getVisaId().equals(visa.getVisaId())).collect(Collectors.toList());
		if(visaList.isEmpty()) return "Invalid Visa Id or Visa Id might be expired.";
		ApplyVisa finalVisa = visaList.get(0);
		if(finalVisa.getDateOfApplication().compareTo(visa.getDateOfApplication())==0){
			LocalDate d=finalVisa.getDateOfExpiry();
			LocalDate now=LocalDate.now();
			if(d.isAfter(now)){
				finalVisa.setDateOfExpiry(now);
				finalVisa.setStatus("expired");
				visaRepository.save(finalVisa);
				//upon successful cancellation of visa user gets a message.
				return "Your request has been submitted successfully.";
				 //Please pay"+ CalculateVisaCancelCharges(visa.getVisaId(), now)+ "to complete the cancellation process";		
			}
			finalVisa.setStatus("expired");
			visaRepository.save(finalVisa);
			return "Visa Expired Already";
		}
		return "Entered date does not match the application date of the visa";
	}

	//gets visa details based on registration id.
	@Override
	public List<ApplyVisa> getByRegId(String regId) {
		return visaRepository.findByRegistrationId(registrationRepository.findById(regId).orElseGet(()->null));
	}

}
