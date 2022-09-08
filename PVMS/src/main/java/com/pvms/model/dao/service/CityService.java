package com.pvms.model.dao.service;

import java.util.List;

import com.pvms.model.pojo.City;
import com.pvms.model.pojo.State;

public interface CityService {
	String getCityName(String cityId);
	List<City> getCityByState(String stateId);
	List<State> getAllStates();
}
