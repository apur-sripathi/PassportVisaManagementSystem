package com.pvms.model.dao.serviceImpl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private static Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>() {{
		put(1, Arrays.asList(2500,2000,4000,6000,1500));
		put(2, Arrays.asList(1500,2000,3000,4000,2000));
    	put(3, Arrays.asList(3500,40000,4500,9000,1000));
		
	}};
	
	@Override
	public String insertVisaDetails(ApplyVisa applyVisa) {
		Registration R = registrationRepository.findById(applyVisa.getRegId()).orElseGet(()->null);
		if(R==null) return "You need to register first";
		applyVisa.setRegistrationId(R);
		ApplyPassport a = passportRepository.findById(applyVisa.getPassid()).orElseGet(()->null);
		if(a==null || !a.getRegistrationId().getId().equals(R.getId())) return "You need a passport";
		if(a.getExpiryDate().isBefore(applyVisa.getDateOfApplication())) return "Passport Expired!!";
		
		Integer cost = calculateCost(applyVisa.getOccupation(),applyVisa.getCountry());
		double permitYears = permitYears(applyVisa.getOccupation());
		applyVisa.setPassId(a);
		LocalDate x = applyVisa.getPassId().getExpiryDate();
		LocalDate y = applyVisa.getDateOfApplication().plusDays(10).plusYears((long) permitYears);
		applyVisa.setDateOfExpiry(y.isAfter(x)?x:y);
		//applyVisa.setDateOfExpiry(null);
		List<ApplyVisa> list = visaRepository.findAll();
		if(!list.isEmpty()) {
			Integer mx = visaRepository.findAll().stream().map(mj->Integer.parseInt(mj.getVisaId())).reduce(0,(ax,b)->Integer.max(ax, b))+1;
			applyVisa.setVisaId(Integer.toString(mx));
		}else {
			applyVisa.setVisaId("1000");
		}
		applyVisa.setStatus("active");
		visaRepository.save(applyVisa);
		return "Dear User your Visa request has been accepted successfully with id"+
		applyVisa.getVisaId()+", User Id "+applyVisa.getRegId()+". Destination "+
		getCountry(applyVisa.getCountry())+", Occupation "+getOccupation(applyVisa.getOccupation())+", Date of Application"+applyVisa.getDateOfApplication()
		+"Date Of Issue"+applyVisa.getDateOfApplication().plusDays(10)+"Date of Expiry"+applyVisa.getDateOfExpiry()+" Registration Cost "
		+calculateCost(applyVisa.getOccupation(),applyVisa.getCountry());  
	}
	
	public Integer calculateCost(Integer occupation,Integer place) {
		return map.get(place).get(occupation-1);
	}
	
	public String getCountry(Integer i) {
		return i==1?"USA":i==2?"CHINA":"JAPAN";
	}
	
	public String getOccupation(Integer i) {
		return i==1?"Student":i==2?"Private Employee ":i==3?"Government Employee":i==4?"Self Employeed":"Retired Eemployee";
	}
	
	public double permitYears(Integer o) {
		return o==1?2:o==2?3:o==3?4:o==4?1:1.5;
	}

	@Override
	public List<ApplyVisa> getVisaDetails() {
		return visaRepository.findAll();
	}

	@Override
	public Double CalculateVisaCancelCharges(String visaId, LocalDate doc) {

		ApplyVisa applyVisa=visaRepository.findById(visaId).get();
		LocalDate dt=applyVisa.getDateOfExpiry();
        Period p=Period.between(doc, dt);
        int Diff_mon=p.getMonths();
		
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

	@Override
	public String cancelVisa(ApplyVisa visa) {
		ApplyVisa applyVisa =   visaRepository.findById(visa.getVisaId()).orElseGet(()->null);
		if(applyVisa!=null || applyVisa.getStatus().equals("active")){
			if(applyVisa.getPassId().getPassId().equals(visa.getPassid())){
				System.out.println(visa.getRegId());
				System.out.println(applyVisa.getRegistrationId().getId());
				System.out.println(applyVisa);
				System.out.println(visa);
				if(applyVisa.getRegistrationId().getId().equals(visa.getRegId())){
					if(applyVisa.getDateOfApplication().compareTo(visa.getDateOfApplication())==0){
						LocalDate d=applyVisa.getDateOfExpiry();
						LocalDate now=LocalDate.now();
						if(d.isAfter(now)){
							applyVisa.setDateOfExpiry(now);
							applyVisa.setStatus("expired");
							visaRepository.save(applyVisa);
							return "Your request has been submitted successfully. Please pay"+ CalculateVisaCancelCharges(visa.getVisaId(), now)+ "to complete the cancellation process";		
						}
						return "Visa Already Expired";
					}
					return "No Visa Found On This Date";
				}
				return "Invalid UserId";
			}
			return "Invalid PassportId";
		}
		
		return "Invalid Visa Number";
	}

	@Override
	public List<ApplyVisa> getByRegId(String regId) {
		return visaRepository.findByRegistrationId(registrationRepository.findById(regId).orElseGet(()->null));
	}

}
