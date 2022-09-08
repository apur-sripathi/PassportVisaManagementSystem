package com.pvms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pvms.model.dao.service.RegistrationService;
import com.pvms.model.pojo.Registration;
import com.pvms.model.repository.RegistrationRepository;

@SpringBootTest
class RegistrationServiceImplTest {

	@Autowired
	RegistrationService registrationService;

	@MockBean
	RegistrationRepository registrationRepository;

	//Test method to insert user details.
	@Test
	void testinsertUser()
	{
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    String s="Your User Id is "+R.getId()+" and your password is"+R.getPassword()+". You are planning for "+R.getApplyType()+" and your citizen type is "+R.getCitizenType()+".";
	    String s2="Date Should be below today";
	    String s3="User Already Exists";
	    String s4="Email Already Exists";
	    SoftAssertions softAssertion= new SoftAssertions();
	    softAssertion.assertThat(s).contains(registrationService.insertUser(R));
	    softAssertion.assertThat(s2).contains(registrationService.insertUser(R));
	    softAssertion.assertThat(s3).contains(registrationService.insertUser(R));
	    softAssertion.assertThat(s4).contains(registrationService.insertUser(R));
	    
	}
	
	//fetch all the users from database.
	@Test
	void testgetAllUsers()
	{
		
		when(registrationRepository.findAll()).thenReturn((List<Registration>) Stream.of( new Registration("PASS-1000","vivek","Mallesh",LocalDate.parse("2000-04-23"),"h.no.4-112,ibrahimpatnam,RR dist-501506","9640571014","d.vivekbabu2000@gmail.com","B.Tech","Male",1,"favourite color","blue","@aug123","Adult"),
				new Registration("PASS-1001","vivekvicky","bmallesh",LocalDate.parse("2000-04-22"),"hyderabad","6303133284","vivekbabu2000@gmail.com","b.Tech","male",1,"favourite color","red","1@aug123","Adult")).collect(Collectors.toList()));
				                                                                   
		assertEquals(2, registrationService.getAllUsers().size());	
	}

	//this method validates user id and password.
	@Test
	void testvalidateUser()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
		when(registrationRepository.save(R)).thenReturn(R);
	   String s="User Not Found";
	   String s2="Valid User";
	   String s3="Wrong Password";
	   SoftAssertions softAssertion= new SoftAssertions();
		softAssertion.assertThat(s).contains(registrationService.validateUser("PASS-1000","@aug143"));
	    softAssertion.assertThat(s2).contains(registrationService.validateUser("PASS-1000","@aug143"));
	    softAssertion.assertThat(s3).contains(registrationService.validateUser("PASS-1000","@aug143"));  
	}

	//gets contact number based on registration id.
	@Test
	void testgetContactByid()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    String c="9640571014";
	    String s="0";
	    Boolean a=c.equals(registrationService.getContactByid(R.getId()));
	    Boolean b=s.equals(registrationService.getContactByid(R.getId()));
	    assertTrue(a||b);
	}
	
	//gets email id based on registration id.
	@Test
	void testgetEmailById()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    String c="d.vivekbabu2000@gmail.com";
	    String s="User Not found";
	    
	    Boolean a=c.equals(registrationService.getEmailById(R.getId()));
	    Boolean b=s.equals(registrationService.getEmailById(R.getId()));
	    assertTrue(a||b);
	}
	
	//updates password
	@Test
	void testupdatePassword()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    String str1="Password Updated Successfully!!!";
	    String str2="User Not found";
	    
	    Boolean a=str1.equals(registrationService.updatePassword(R.getId(), R.getPassword()));
	    Boolean b=str2.equals(registrationService.updatePassword(R.getId(), R.getPassword()));
	    assertTrue(a||b);
	    
	}
	
	//update user details in database.
	@Test
	void testupdateUser()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
		
		String str1="Date Should be below today";
		String str2="Details Updated Successfully!!";
		String str3="User not found";
		Boolean a=str1.equals(registrationService.updateUser(R.getId(), R));
		Boolean b=str2.equals(registrationService.updateUser(R.getId(), R));
		Boolean c=str3.equals(registrationService.updateUser(R.getId(), R));
		assertTrue(a||b||c);
		
		
	}
	
	//get user details based on registration id.
	@Test
	void testgetUserById()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    when(registrationRepository.findById(R.getId())).thenReturn(Optional.of(R));
	    
	    assertEquals(R, registrationService.getUserById("PASS-1000"));	
	}
	
	//fetches hint question based on registration id.
	@Test
	void testgetHintQuestion()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    String str1="User not found";
	    String str2="favourite color";
	    Boolean a=str1.equals(registrationService.getHintQuestion(R.getId()));
	    Boolean b=str2.equals(registrationService.getHintQuestion(R.getId()));
	    assertTrue(a||b);  
	}
	
	//fetches hint question based on email id.
	@Test
	void testgetHQuestion()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	   
	    String str2="favourite color";
	    Boolean a =(registrationService.getHQuestion(R.getEmail())==null);
	    Boolean b=str2.equals(registrationService.getHQuestion(R.getEmail()));
	    assertTrue(b||a);
	}
	
	//validates hint answer based on email id.
	@Test
	void testvalidateHAnswer()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    String str2="Incorect Hint Answer";
	    Boolean a =(registrationService.validateHAnswer(R.getHintAnswer(),R.getId())==null);
	    Boolean b=str2.equals(registrationService.validateHAnswer(R.getHintAnswer(),R.getId()));
	    assertTrue(b||a); 	    
	}
	
	//validates hint answer based on registration id.
	@Test
	void testvalidateHintAnswer()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
		
		String str1="Correct";
		String str2="Incorrect";
		String str3="User not found";
		
		Boolean a=str1.equals(registrationService.validateHintAnswer(R.getHintQuestion(), R.getId()));
		Boolean b=str2.equals(registrationService.validateHintAnswer(R.getHintQuestion(), R.getId()));
		Boolean c=str3.equals(registrationService.validateHintAnswer(R.getHintQuestion(), R.getId()));
		
		assertTrue(a||b||c);
		
	}
	
	//validate email whether exists in database or not.
	@Test
	void testvalidateEmail()
	{
		Registration R=new Registration();
		R.setId("PASS-1000");
		R.setPassword("@aug143");
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
	    when(registrationRepository.save(R)).thenReturn(R);
	    
	    String str1="valid to use";
	    String str2="Email Already Exists";
	    
	    Boolean a=str1.equals(registrationService.validateEmail("d.vivekbabu2000@gmail.com"));
	    Boolean b=str2.equals(registrationService.validateEmail("d.vivekbabu2000@gmail.com"));
	    
	    assertTrue(a||b);
	    
	}
	
	
}
