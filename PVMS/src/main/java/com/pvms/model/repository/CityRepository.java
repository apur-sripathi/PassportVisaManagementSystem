package com.pvms.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pvms.model.pojo.City;
import com.pvms.model.pojo.State;

public interface CityRepository extends JpaRepository<City, String> {
	//fetches all cities based on state Id(group by stateId);
	List<City> findCityIdByStateId(State stateId);
}
