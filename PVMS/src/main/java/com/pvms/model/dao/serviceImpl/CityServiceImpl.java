package com.pvms.model.dao.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvms.model.dao.service.CityService;
import com.pvms.model.pojo.City;
import com.pvms.model.pojo.State;
import com.pvms.model.repository.CityRepository;
import com.pvms.model.repository.StateRepository;

@Service
public class CityServiceImpl implements CityService{
	
	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	StateRepository stateRepository;
	
	//it will fetch city name based on city id(pK)
	@Override
	public String getCityName(String cityId) {
		City  C= cityRepository.findById(cityId).orElseGet(()->null);
		return C==null?"City Not Found":C.getCityName();
	}

	//it will fetch cities based on state id(pk of state).
	@Override
	public List<City> getCityByState(String stateId) {
		State S=stateRepository.findById(stateId).orElseGet(()->null);
		return S==null?null:cityRepository.findCityIdByStateId(S);
	}

	//it will fetch all the states.
	@Override
	public List<State> getAllStates() {
		return stateRepository.findAll();
	}
	
	//it fetch state by its id.
	public State getStateById(String id) {
		return stateRepository.findById(id).orElseGet(()->null);
	}

	
}
