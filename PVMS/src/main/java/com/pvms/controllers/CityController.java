package com.pvms.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pvms.model.dao.serviceImpl.CityServiceImpl;
import com.pvms.model.pojo.City;
import com.pvms.model.pojo.State;

@RestController
@RequestMapping("/city")
public class CityController {
	
	@Autowired
	CityServiceImpl cityServiceImpl;
	
	@GetMapping("getCityName/{cityId}")
	public String getCityName(@PathVariable("cityId") String cityId)
	{
	return cityServiceImpl.getCityName(cityId);	
	}
	
	
	@GetMapping("getCityByState/{stateId}")
	public List<City> getCityByState(@PathVariable("stateId") String stateId)
	{
	return cityServiceImpl.getCityByState(stateId);	
	}
	
	@GetMapping("getAllState")
	public List<State> getAllState()
	{
	return cityServiceImpl.getAllStates();	
	}
	
	@GetMapping("getStateById/{id}")
	public State getStateById(@PathVariable("id") String id)
	{
	return cityServiceImpl.getStateById(id);	
	}
}
