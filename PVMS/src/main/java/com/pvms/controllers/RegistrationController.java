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

	@PostMapping("insertUser")
	public String insertUser(@RequestBody Registration R) {
		return registrationServiceImpl.insertUser(R);
	}
	
	@GetMapping("getUsers")
	public List<Registration> getUsers(){
		return registrationServiceImpl.getAllUsers();
	}
	
	@GetMapping("getUserById/{id}")
	public Registration getUserById(@PathVariable("id") String id){
		return registrationServiceImpl.getUserById(id);
	}
	
	@GetMapping("getLastRecord")
	public Registration getLastRecord(){
		return registrationServiceImpl.lastRecord();
	}
	
	@GetMapping("getContactById/{id}")
	public String getContactById(@PathVariable("id") String id) {
		return registrationServiceImpl.getContactByid(id);
	}
	
	@GetMapping("getEmailById/{id}")
	public String getEmailById(@PathVariable("id") String id) {
		return registrationServiceImpl.getEmailById(id);
	}
	
	@PutMapping("updatePassword/{id}/{pass}")
	public String updatePassword(@PathVariable("id") String id,@PathVariable("pass") String pass) {
		return registrationServiceImpl.updatePassword(id, pass);
	}
	
	@PutMapping("updateUser/{id}")
	public String updateUser(@PathVariable("id") String id,@RequestBody Registration R) {
		return registrationServiceImpl.updateUser(id,R);
	}
	
	@GetMapping("getHintQuestion/{id}")
	public String getHintQuestion(@PathVariable("id") String id)
	{
		return registrationServiceImpl.getHintQuestion(id);
	}
	
	@GetMapping("getHQuestion/{id}")
	public String getHQuestion(@PathVariable("id") String id)
	{
		return registrationServiceImpl.getHQuestion(id);
	}
	
	@GetMapping("validateHintAnswer/{id}/{hintAnswer}")
	public String validateHintAnswer(@PathVariable("id") String id,@PathVariable("hintAnswer") String hintAnswer )
	{
		return registrationServiceImpl.validateHintAnswer(hintAnswer, id);
	}	
	
	@GetMapping("validateHAnswer/{id}/{hintAnswer}")
	public String validateHAnswer(@PathVariable("id") String id,@PathVariable("hintAnswer") String hintAnswer )
	{
		return registrationServiceImpl.validateHAnswer(hintAnswer, id);
	}	
	
	@GetMapping("validateUser/{id}/{pwd}")
	public String validateUser(@PathVariable("id") String id,@PathVariable("pwd") String pwd) {
		return registrationServiceImpl.validateUser(id, pwd);
	}
	
	@GetMapping("validatemail/{mail}")
	public String validateMail(@PathVariable("mail") String mail) {
		return registrationServiceImpl.validateEmail(mail);
	}
}
