package com.pvms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pvms.model.dao.service.CityService;
import com.pvms.model.dao.service.PassportService;
import com.pvms.model.dao.service.RegistrationService;
import com.pvms.model.dao.serviceImpl.PassportServiceImpl;
import com.pvms.model.pojo.ApplyPassport;
import com.pvms.model.pojo.City;
import com.pvms.model.pojo.Registration;
import com.pvms.model.pojo.State;
import com.pvms.model.repository.CityRepository;
import com.pvms.model.repository.PassportRepository;
import com.pvms.model.repository.RegistrationRepository;
import com.pvms.model.repository.StateRepository;

@SpringBootTest
class PassportServiceImplTest {

	@Autowired
	RegistrationService registrationService;
	
	@MockBean
	RegistrationRepository registrationRepository;
	
	@MockBean
	StateRepository stateRepository;
	
	@Autowired
	PassportService passportService;
	
	@Autowired
	PassportServiceImpl passportServiceImpl;
	
	@MockBean
	PassportRepository passportRepository;

	@Autowired
	CityService cityServiceImpl;
	
	@MockBean
	CityRepository cityRepository;

	//Test method for insertion of passport details.
	@Test
	void testinsertPassportDetails(){
		ApplyPassport applyPassport=new ApplyPassport();
		Registration R=new Registration();
		R.setfName("vivek");
		R.setSurName("Mallesh");
		R.setDob(LocalDate.parse("2000-04-23"));
		R.setAddress("h.no.4-112,ibrahimpatnam,RR dist-501506");
		R.setContactNo("9640571014");
		R.setEmail("d.vivekbabu2000@gmail.com");
		R.setQualification("B.Tech");
		R.setGender("Male");
	    R.setApplyType(1);
	    R.setHintQuestion("favourite color");
	    R.setHintAnswer("blue");
	    R.setId("PASS-1000");
	    when(registrationRepository.save(R)).thenReturn(R);
		Registration R2=registrationService.getUserById(R.getId());
		
		State S=new State();
		S.setStateId("SO_001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		City C=new City();
		C.setCityId("CO_001");
		C.setCityName("Bhubaneswar");
		when(cityRepository.save(C)).thenReturn(C);
		
		applyPassport.setRegistrationId(R2);
		applyPassport.setCountry("India");
		applyPassport.setStateId(S);
		applyPassport.setCityId(C);
		applyPassport.setPin(501506);
		applyPassport.setTypeOfService(1);
        applyPassport.setBookletType(1);
        applyPassport.setIssueDate(LocalDate.parse("2022-08-31"));
        applyPassport.setExpiryDate(null);
        when(passportRepository.save(applyPassport)).thenReturn(applyPassport);
        
        String str1="Enter valid Id";
        String str2="You already have a passport";
        String str3="'"+applyPassport.getPassId()+"'"+". Passport application cost is Rs."+passportServiceImpl.calculateCost(applyPassport.getTypeOfService());
      
        Boolean a=str1.equals(passportService.insertPassportDetails(applyPassport));
        Boolean b=str2.equals(passportService.insertPassportDetails(applyPassport));
        Boolean c=str3.equals(passportService.insertPassportDetails(applyPassport));
        
        assertTrue(a||b||c);	
	}

	//Test method for fetching all the passport records.
	@Test
	void testgetAllPassports(){
		ApplyPassport applyPassport=new ApplyPassport();
		Registration R=new Registration();
		R.setfName("vivek");
		R.setSurName("Mallesh");
		R.setDob(LocalDate.parse("2000-04-23"));
		R.setAddress("h.no.4-112,ibrahimpatnam,RR dist-501506");
		R.setContactNo("9640571014");
		R.setEmail("d.vivekbabu2000@gmail.com");
		R.setQualification("B.Tech");
		R.setGender("Male");
	    R.setApplyType(1);
	    R.setHintQuestion("favourite color");
	    R.setHintAnswer("blue");
	    R.setId("PASS-1000");
	    when(registrationRepository.save(R)).thenReturn(R);
		Registration R2=registrationService.getUserById(R.getId());
		
		State S=new State();
		S.setStateId("SO_001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		City C=new City();
		C.setCityId("CO_001");
		C.setCityName("Bhubaneswar");
		when(cityRepository.save(C)).thenReturn(C);
		
		when(passportRepository.save(applyPassport)).thenReturn(applyPassport);
		when(passportRepository.findAll()).thenReturn(
				(List<ApplyPassport>) Stream.of(new ApplyPassport("FPS-301000",R2,"India",S,C,501506,1,1,LocalDate.parse("2022-08-31"),null),
						new ApplyPassport("FPS-301000",R2,"India",S,C,501506,1,1,LocalDate.parse("2022-08-31"),null)).collect(Collectors.toList()));
	    assertEquals(2, passportService.getAllPassports().size());        
	}

	//Test Method for reissuing passport details.
	@Test
	void testreissuePassport()
	{
		
		ApplyPassport applyPassport=new ApplyPassport();
		Registration R=new Registration();
		R.setfName("vivek");
		R.setSurName("Mallesh");
		R.setDob(LocalDate.parse("2000-04-23"));
		R.setAddress("h.no.4-112,ibrahimpatnam,RR dist-501506");
		R.setContactNo("9640571014");
		R.setEmail("d.vivekbabu2000@gmail.com");
		R.setQualification("B.Tech");
		R.setGender("Male");
	    R.setApplyType(1);
	    R.setHintQuestion("favourite color");
	    R.setHintAnswer("blue");
	    R.setId("PASS-1000");
	    when(registrationRepository.save(R)).thenReturn(R);
		Registration R2=registrationService.getUserById(R.getId());
		
		State S=new State();
		S.setStateId("SO_001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		City C=new City();
		C.setCityId("CO_001");
		C.setCityName("Bhubaneswar");
		when(cityRepository.save(C)).thenReturn(C);
		
		applyPassport.setRegistrationId(R2);
		applyPassport.setCountry("India");
		applyPassport.setStateId(S);
		applyPassport.setCityId(C);
		applyPassport.setPin(501506);
		applyPassport.setTypeOfService(1);
        applyPassport.setBookletType(1);
        applyPassport.setIssueDate(LocalDate.parse("2022-08-31"));
        applyPassport.setExpiryDate(null);
		when(passportRepository.save(applyPassport)).thenReturn(applyPassport);
		 
		String str1="You need to Apply to reissue a passport";
		String str2="Passport reissue is successfully done.\nAmount to be paid is "+passportServiceImpl.calculateRenewalCost(applyPassport.getTypeOfService())+
				".\n Passport issue date is "+applyPassport.getIssueDate()+" and expiry date is "+applyPassport.getIssueDate().plusYears(10)+".";

	    Boolean a=str1.equals(passportService.reissuePassport(applyPassport));
	    Boolean b=str2.equals(passportService.reissuePassport(applyPassport));
	    
	    assertTrue(a||b);
	}
	
	
	//Test Method for getting passport details based on registration id.
	@Test
	void testgetPassportByRegistrationId()
	{
		ApplyPassport applyPassport=new ApplyPassport();
		Registration R=new Registration();
		R.setfName("vivek");
		R.setSurName("Mallesh");
		R.setDob(LocalDate.parse("2000-04-23"));
		R.setAddress("h.no.4-112,ibrahimpatnam,RR dist-501506");
		R.setContactNo("9640571014");
		R.setEmail("d.vivekbabu2000@gmail.com");
		R.setQualification("B.Tech");
		R.setGender("Male");
	    R.setApplyType(1);
	    R.setHintQuestion("favourite color");
	    R.setHintAnswer("blue");
	    R.setId("PASS-1000");
	    when(registrationRepository.save(R)).thenReturn(R);
		Registration R2=registrationService.getUserById(R.getId());
		
		State S=new State();
		S.setStateId("SO_001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		City C=new City();
		C.setCityId("CO_001");
		C.setCityName("Bhubaneswar");
		when(cityRepository.save(C)).thenReturn(C);
		
		applyPassport.setRegistrationId(R2);
		applyPassport.setCountry("India");
		applyPassport.setStateId(S);
		applyPassport.setCityId(C);
		applyPassport.setPin(501506);
		applyPassport.setTypeOfService(1);
        applyPassport.setBookletType(1);
        applyPassport.setIssueDate(LocalDate.parse("2022-08-31"));
        applyPassport.setExpiryDate(null);
		when(passportRepository.save(applyPassport)).thenReturn(applyPassport);
		when(passportService.getPassportByRegistrationId("PASS-1000")).thenReturn((List<ApplyPassport>) Stream.of(applyPassport).collect(Collectors.toList()));
		assertEquals(1, passportService.getPassportByRegistrationId("PASS-1000").size());	 
	}
	
	//Method to fetch recent passport.
	@Test
	void testgetRecentPassport()
	{
		ApplyPassport applyPassport=new ApplyPassport();
		Registration R=new Registration();
		R.setfName("vivek");
		R.setSurName("Mallesh");
		R.setDob(LocalDate.parse("2000-04-23"));
		R.setAddress("h.no.4-112,ibrahimpatnam,RR dist-501506");
		R.setContactNo("9640571014");
		R.setEmail("d.vivekbabu2000@gmail.com");
		R.setQualification("B.Tech");
		R.setGender("Male");
	    R.setApplyType(1);
	    R.setHintQuestion("favourite color");
	    R.setHintAnswer("blue");
	    R.setId("PASS-1000");
	    when(registrationRepository.save(R)).thenReturn(R);
		Registration R2=registrationService.getUserById(R.getId());
		
		State S=new State();
		S.setStateId("SO_001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		City C=new City();
		C.setCityId("CO_001");
		C.setCityName("Bhubaneswar");
		when(cityRepository.save(C)).thenReturn(C);
		
		applyPassport.setRegistrationId(R2);
		applyPassport.setCountry("India");
		applyPassport.setStateId(S);
		applyPassport.setCityId(C);
		applyPassport.setPin(501506);
		applyPassport.setTypeOfService(1);
        applyPassport.setBookletType(1);
        applyPassport.setIssueDate(LocalDate.parse("2022-08-31"));
        applyPassport.setExpiryDate(null);
		when(passportRepository.save(applyPassport)).thenReturn(applyPassport);
		assertEquals(applyPassport, passportService.getRecenetPassport(R.getId()));	
	}
	
}
