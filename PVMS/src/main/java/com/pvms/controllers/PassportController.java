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
	
	//fetches all the passport details from the database.
	@GetMapping("/getPassports")
	public List<ApplyPassport> getAllPassports(){
		return passportServiceImpl.getAllPassports();
	}
	
	//inserts passport details entered by user to database.
	@PostMapping("/insertPassport")
	public String insertPassport(@RequestBody ApplyPassport applyPassport) {
		return passportServiceImpl.insertPassportDetails(applyPassport);
	}
	
	//reinsert passport details into database.(Passport ReIssue)
	@PostMapping("/reIssuePassport")
	public String reIssuePassport(@RequestBody ApplyPassport applyPassport) {
		return passportServiceImpl.reissuePassport(applyPassport);
	}
	
	//fetches all the passport records based on registration id of the user.
	@GetMapping("getpassportbyregid/{id}")
	public List<ApplyPassport> getPassportByRegId(@PathVariable("id") String id)
	{
		return passportServiceImpl.getPassportByRegistrationId(id);
	}
	
	//fetches passport of the user whose expiry date is not yet finished.
	@GetMapping("getrecentpassport/{id}")
	public ApplyPassport getRecentPassport(@PathVariable("id") String id)
	{
		return passportServiceImpl.getRecenetPassport(id);
	}	
}
