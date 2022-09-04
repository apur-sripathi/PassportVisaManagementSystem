package com.pvms.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class City {
	@Id
	@Column(length=6)
	private String cityId;
	@Column(length=30)
	private String cityName;
	@ManyToOne
	@JoinColumn(name = "stateId",referencedColumnName = "stateId")
	private State stateId;
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public State getStateId() {
		return stateId;
	}
	public void setStateId(State stateId) {
		this.stateId = stateId;
	}
	public City(String cityId, String cityName, State stateId) {
		super();
		this.cityId = cityId;
		this.cityName = cityName;
		this.stateId = stateId;
	}
	public City() {
		super();
	}
	@Override
	public String toString() {
		return "City [cityId=" + cityId + ", cityName=" + cityName + ", stateId=" + stateId + "]";
	}
	
	
	
	
	
}
