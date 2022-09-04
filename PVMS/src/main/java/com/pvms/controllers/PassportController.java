package com.pvms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pvms.model.dao.serviceImpl.PassportServiceImpl;
import com.pvms.model.pojo.ApplyPassport;

@RestController
@RequestMapping("/passport")
public class PassportController {
	
	@Autowired
	PassportServiceImpl passportServiceImpl;
	
	@GetMapping("/getPassports")
	public List<ApplyPassport> getAllPassports(){
		return passportServiceImpl.getAllPassports();
	}
	
	@PostMapping("/insertPassport")
	public String insertPassport(@RequestBody ApplyPassport applyPassport) {
		return passportServiceImpl.insertPassportDetails(applyPassport);
	}
	
	@PostMapping("/reIssuePassport")
	public String reIssuePassport(@RequestBody ApplyPassport applyPassport) {
		return passportServiceImpl.reissuePassport(applyPassport);
	}
	
	@GetMapping("getpassportbyregid/{id}")
	public List<ApplyPassport> getPassportByRegId(@PathVariable("id") String id)
	{
		return passportServiceImpl.getPassportByRegistrationId(id);
	}
	
	@GetMapping("getrecentpassport/{id}")
	public ApplyPassport getRecentPassport(@PathVariable("id") String id)
	{
		return passportServiceImpl.getRecenetPassport(id);
	}
	
	
	
}
