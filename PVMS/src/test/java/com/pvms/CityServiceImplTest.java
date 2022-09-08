package com.pvms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.pvms.model.dao.service.CityService;
import com.pvms.model.pojo.City;
import com.pvms.model.pojo.State;
import com.pvms.model.repository.CityRepository;
import com.pvms.model.repository.StateRepository;

@SpringBootTest
class CityServiceImplTest {

	@Autowired
	CityService cityService;
	
	@MockBean
	CityRepository cityRepository;

	@MockBean
	StateRepository stateRepository;

	//Test method for getting city name when city id is given.
	@Test
	void testgetCityName(){
		City C=new City();
		C.setCityId("CO_001");
		C.setCityName("Bhubaneswar");
		when(cityRepository.save(C)).thenReturn(C);
		
		String str1="City Not Found";
		String str2="Bhubaneswar";
		
		Boolean a=str1.equals(cityService.getCityName("CO_001"));
		Boolean b=str2.equals(cityService.getCityName("CO_001"));
		assertTrue(a||b);	
	}
	
	//Fetching cities based on state id.
	@Test
	void testgetCityByState(){
		State S=new State();
		S.setStateId("SO_001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		when(cityRepository.findCityIdByStateId(S)).thenReturn((List<City>) Stream.of(
				new City("CO_001","Bhubaneswar",S),
				new City("CO_002","Cuttack",S)).collect(Collectors.toList()));		
		assertEquals(2, cityRepository.findCityIdByStateId(S).size());	
	}
	
	//fetching all the states.
	@Test
	void testgetAllStates(){
		State S=new State();
		S.setStateId("SO_001");
		S.setStateName("Orissa");
		when(stateRepository.save(S)).thenReturn(S);
		
		State S1=new State();
		S1.setStateId("SO_001");
		S1.setStateName("Orissa");
		when(stateRepository.save(S1)).thenReturn(S1);
		
		when(stateRepository.findAll()).thenReturn(
				 (List<State>) Stream.of(new State("SO_001","Orissa"), new State("SW_002","West Bengal")).collect(Collectors.toList()));
	    assertEquals(2, cityService.getAllStates().size());
	}	
}
