package com.pvms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pvms.model.dao.service.CityService;
import com.pvms.model.dao.service.PassportService;
import com.pvms.model.dao.service.RegistrationService;
import com.pvms.model.dao.service.VisaService;
import com.pvms.model.dao.serviceImpl.PassportServiceImpl;
import com.pvms.model.pojo.ApplyPassport;
import com.pvms.model.pojo.ApplyVisa;
import com.pvms.model.pojo.City;
import com.pvms.model.pojo.Registration;
import com.pvms.model.pojo.State;
import com.pvms.model.repository.CityRepository;
import com.pvms.model.repository.PassportRepository;
import com.pvms.model.repository.RegistrationRepository;
import com.pvms.model.repository.StateRepository;
import com.pvms.model.repository.VisaRepository;

@SpringBootTest
class VisaServiceImplTest {

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
	
	@MockBean
	VisaRepository visaRepository;
	
	@Autowired
	VisaService visaService;
	
	//Insert visa details into database.
	@Test
	void testinsertVisaDetails(){
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

	  ApplyVisa applyVisa=new ApplyVisa();
	  applyVisa.setPassId(applyPassport);
	  applyVisa.setRegistrationId(R);
	  applyVisa.setCountry(1);
	  applyVisa.setOccupation(3);
	  applyVisa.setDateOfApplication(LocalDate.now());
	   
	  when(visaRepository.save(applyVisa)).thenReturn(applyVisa);
	  
	  String str1="You need to register first";
	  String str2="You need a passport";
	  String str3="Dear User your Visa request has been accepted successfully with id"+
				applyVisa.getVisaId()+", User Id "+applyVisa.getRegId()+". Destination "+
				applyVisa.getCountry()+" Employee Occupation "+applyVisa.getOccupation()+" Date of Application";
	  
	  Boolean a=str1.equals( visaService.insertVisaDetails(applyVisa));
	  Boolean b=str2.equals( visaService.insertVisaDetails(applyVisa));
	  Boolean c=str3.equals( visaService.insertVisaDetails(applyVisa));
      
	  assertTrue(a||b||c);
	}
	
	//gets visa details of all the users
	@Test
	void testgetVisaDetails()
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
		
		when(visaRepository.findAll()).thenReturn((List<ApplyVisa>)   Stream.of(
				        new ApplyVisa("VISA-10000002",R,1,2,LocalDate.parse("2022-04-23"),LocalDate.parse("2032-04-23"),applyPassport,"active"),
						new ApplyVisa("VISA-10000003",R,1,2,LocalDate.parse("2022-04-23"),LocalDate.parse("2032-04-23"),applyPassport,"active")).collect(Collectors.toList())
				);
		
		assertEquals(2, visaService.getVisaDetails().size());	
	}
	
	//calculates cancel charges for visa
	@Test
	void testCalculateVisaCancelCharges()
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
		
		State S=new State();
		S.setStateId("S0001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		City C=new City();
		C.setCityId("C0001");
		C.setCityName("Bhubaneswar");
		C.setStateId(S);
		when(cityRepository.save(C)).thenReturn(C);
		
		applyPassport.setPassId("FPS-301000");
		applyPassport.setRegistrationId(R);
		applyPassport.setCountry("India");
		applyPassport.setStateId(S);
		applyPassport.setCityId(C);
		applyPassport.setPin(501506);
		applyPassport.setTypeOfService(1);
        applyPassport.setBookletType(1);
        applyPassport.setIssueDate(LocalDate.parse("2022-08-31"));
        applyPassport.setExpiryDate(LocalDate.parse("2032-08-31"));
		
        when(passportRepository.save(applyPassport)).thenReturn(applyPassport);
		
        ApplyVisa applyVisa=new ApplyVisa("1002",R,1,2,LocalDate.parse("2022-04-23"),LocalDate.parse("2024-04-23"),applyPassport,"active");
        ApplyVisa visa = new ApplyVisa();
        visa.setCountry(1);
        visa.setDateOfApplication(LocalDate.parse("2022-04-23"));
        visa.setDateOfExpiry(LocalDate.parse("2024-04-23"));
        visa.setOccupation(2);
        visa.setPassId(applyPassport);
        visa.setReason(0);
        visa.setRegistrationId(R);
        visa.setStatus("active");
        visa.setVisaId("1001");
		when(visaRepository.findById(visa.getVisaId())).thenReturn(Optional.of(visa));
		assertEquals(2000.0, visaService.CalculateVisaCancelCharges(visa.getVisaId(),LocalDate.now()));
	}
	
	//cancel visa method.
	@Test
	void testcancelVisa()
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
		
		State S=new State();
		S.setStateId("S0001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		City C=new City();
		C.setCityId("C0001");
		C.setCityName("Bhubaneswar");
		when(cityRepository.save(C)).thenReturn(C);
		
		applyPassport.setPassId("FPS-301000");
		applyPassport.setRegistrationId(R);
		applyPassport.setCountry("India");
		applyPassport.setStateId(S);
		applyPassport.setCityId(C);
		applyPassport.setPin(501506);
		applyPassport.setTypeOfService(1);
        applyPassport.setBookletType(1);
        applyPassport.setIssueDate(LocalDate.parse("2022-08-31"));
   
        when(passportRepository.findById(applyPassport.getPassId())).thenReturn(Optional.of(applyPassport));
        ApplyVisa visa = new ApplyVisa();
        visa.setCountry(1);
        visa.setDateOfApplication(LocalDate.parse("2022-04-23"));
        visa.setDateOfExpiry(LocalDate.parse("2024-04-23"));
        visa.setOccupation(2);
        visa.setPassId(applyPassport);
        visa.setPassid(applyPassport.getPassId());
        visa.setReason(0);
        visa.setRegistrationId(R);
        visa.setStatus("active");
        visa.setVisaId("1001");
      
        
		when(visaRepository.findById(visa.getVisaId())).thenReturn(Optional.of(visa));
		String str1="Your request has been submitted successfully.";
		String str2="There does not exist any Visa for the given Passport id : "+visa.getPassid();
		String str3="There is no active Visa";
		String str4="Invalid Visa Id or Visa Id might be expired.";
		String str5="Visa Expired Already";
		String str6="Entered date does not match the application date of the visa";
		String str7="Visa Expired Already";
		String str8="Entered date does not match the application date of the visa";
		
		Boolean a=str1.equals(visaService.cancelVisa(visa));
		Boolean b=str2.equals(visaService.cancelVisa(visa));
		Boolean c=str3.equals(visaService.cancelVisa(visa));
		Boolean d=str4.equals(visaService.cancelVisa(visa));
		Boolean e=str5.equals(visaService.cancelVisa(visa));
		Boolean f=str6.equals(visaService.cancelVisa(visa));	
		Boolean g=str7.equals(visaService.cancelVisa(visa));
		Boolean h=str8.equals(visaService.cancelVisa(visa));
		assertTrue(a||b||c||d||e||f||g||h);
	}
	
	
	//get visa details based on registration id.
	@Test
	void testgetByRegId()
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
        when(passportRepository.save(applyPassport)).thenReturn(applyPassport); 
        ApplyVisa applyVisa=new ApplyVisa("VISA-10000002",R,1,2,LocalDate.parse("2022-04-23"),LocalDate.parse("2032-04-23"),applyPassport,"active");
		when(visaRepository.save(applyVisa)).thenReturn(applyVisa);
		assertEquals(0, visaService.getByRegId(R.getId()).size());

	}
	
}
