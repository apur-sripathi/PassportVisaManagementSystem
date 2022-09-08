package com.pvms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pvms.model.dao.serviceImpl.VisaServiceImpl;
import com.pvms.model.pojo.ApplyVisa;

@RestController
@RequestMapping("/visa")
public class VisaController {
		
	@Autowired
	VisaServiceImpl visaServiceImpl;
	
	//gets all the visa details.
	@GetMapping("/getVisaDetails")
	public List<ApplyVisa> getVisaDetails(){
		return visaServiceImpl.getVisaDetails();
	}
	
	//gets visa details based on user id.
	@GetMapping("/getVisaByRegId/{id}")
	public List<ApplyVisa> getByRegId(@PathVariable("id") String id){
		return visaServiceImpl.getByRegId(id);
	}
	//Inserts visa details into database
	@PostMapping("/insertVisa")
	public String insertVisa(@RequestBody ApplyVisa applyVisa) {
		return visaServiceImpl.insertVisaDetails(applyVisa);
	}
	//cancels visa by making status="expired";
	@PostMapping("/cancelVisa")
	public String cancelVisa(@RequestBody ApplyVisa applyVisa) {
		return visaServiceImpl.cancelVisa(applyVisa);
	}
	
}
