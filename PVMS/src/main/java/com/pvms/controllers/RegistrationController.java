package com.pvms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pvms.model.dao.serviceImpl.RegistrationServiceImpl;
import com.pvms.model.pojo.Registration;

@RestController
@RequestMapping("/user/")
public class RegistrationController {
	
	@Autowired
	RegistrationServiceImpl registrationServiceImpl;

	//Inserts user details entered by the user to database.
	@PostMapping("insertUser")
	public String insertUser(@RequestBody Registration R) {
		return registrationServiceImpl.insertUser(R);
	}
	
	//fetches all the user details from the database.
	@GetMapping("getUsers")
	public List<Registration> getUsers(){
		return registrationServiceImpl.getAllUsers();
	}
	
	//fetches user details based on the registration id.
	@GetMapping("getUserById/{id}")
	public Registration getUserById(@PathVariable("id") String id){
		return registrationServiceImpl.getUserById(id);
	}
	
	//fetches contact based on the registration id.
	@GetMapping("getContactById/{id}")
	public String getContactById(@PathVariable("id") String id) {
		return registrationServiceImpl.getContactByid(id);
	}
	
	//fetches email based on registration id.
	@GetMapping("getEmailById/{id}")
	public String getEmailById(@PathVariable("id") String id) {
		return registrationServiceImpl.getEmailById(id);
	}
	
	//updates password based on id and new password
	@PutMapping("updatePassword/{id}/{pass}")
	public String updatePassword(@PathVariable("id") String id,@PathVariable("pass") String pass) {
		return registrationServiceImpl.updatePassword(id, pass);
	}
	
	//update user details
	@PutMapping("updateUser/{id}")
	public String updateUser(@PathVariable("id") String id,@RequestBody Registration R) {
		return registrationServiceImpl.updateUser(id,R);
	}
	
	//get hint question based on user id.
	@GetMapping("getHintQuestion/{id}")
	public String getHintQuestion(@PathVariable("id") String id)
	{
		return registrationServiceImpl.getHintQuestion(id);
	}
	//get hint question based on email id.
	@GetMapping("getHQuestion/{id}")
	public String getHQuestion(@PathVariable("id") String id)
	{
		return registrationServiceImpl.getHQuestion(id);
	}
	//validate hint answer based on user id.
	@GetMapping("validateHintAnswer/{id}/{hintAnswer}")
	public String validateHintAnswer(@PathVariable("id") String id,@PathVariable("hintAnswer") String hintAnswer )
	{
		return registrationServiceImpl.validateHintAnswer(hintAnswer, id);
	}	
	//validate hint answer based on email id.
	@GetMapping("validateHAnswer/{id}/{hintAnswer}")
	public String validateHAnswer(@PathVariable("id") String id,@PathVariable("hintAnswer") String hintAnswer )
	{
		return registrationServiceImpl.validateHAnswer(hintAnswer, id);
	}	
	
	//validate user details based on user id and password.
	@GetMapping("validateUser/{id}/{pwd}")
	public String validateUser(@PathVariable("id") String id,@PathVariable("pwd") String pwd) {
		return registrationServiceImpl.validateUser(id, pwd);
	}
	
	//validate email id.
	@GetMapping("validatemail/{mail}")
	public String validateMail(@PathVariable("mail") String mail) {
		return registrationServiceImpl.validateEmail(mail);
	}
}
