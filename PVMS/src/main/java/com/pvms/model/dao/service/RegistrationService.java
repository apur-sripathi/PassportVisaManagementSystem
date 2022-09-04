package com.pvms.model.dao.service;

import java.util.List;

import com.pvms.model.pojo.Registration;

public interface RegistrationService {
	String insertUser(Registration R);
	List<Registration> getAllUsers();
	Registration lastRecord();
	Registration getUserById(String id);
	String validateUser(String username,String password);
	String getContactByid(String id);
	String getEmailById(String id);
	String updatePassword(String username,String password);
	String updateUser(String id,Registration R);
	String getHintQuestion(String id);
	String getHQuestion(String id);
	String validateHAnswer(String hintAnswer, String id);
	String validateHintAnswer(String hintQuestion,String id);
	String validateEmail(String mail);
}
