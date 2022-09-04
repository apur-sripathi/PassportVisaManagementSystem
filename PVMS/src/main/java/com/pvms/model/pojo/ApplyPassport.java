package com.pvms.model.pojo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

@Entity
@Table(name="PassportDetails")
public class ApplyPassport {

	@Id
	@Column(length=10)
	private String passId;
	@ManyToOne
	@JoinColumn(name="id",referencedColumnName = "id")
	private Registration registrationId;
	@Column(length=30)
	private String country;
	@ManyToOne
	@JoinColumn(name="stateId",referencedColumnName = "stateId")
	private State stateId;
	@ManyToOne
	@JoinColumn(name="cityId",referencedColumnName = "cityId")
	private City cityId;
	@Column(length=6)
	private int pin;
	@Column(length=1)
	private int typeOfService;
	@Column(length=1)
	private int bookletType;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate issueDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true)
	private LocalDate expiryDate;
	
	@Transient
	private String registrationIdt;
	@Transient
	private String stateIdt;
	@Transient
	private String cityIdt;
	public String getPassId() {
		return passId;
	}
	public void setPassId(String passId) {
		this.passId = passId;
	}
	public Registration getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(Registration registrationId) {
		this.registrationId = registrationId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public State getStateId() {
		return stateId;
	}
	public void setStateId(State stateId) {
		this.stateId = stateId;
	}
	public City getCityId() {
		return cityId;
	}
	public void setCityId(City cityId) {
		this.cityId = cityId;
	}
	public int getPin() {
		return pin;
	}
	public void setPin(int pin) {
		this.pin = pin;
	}
	public int getTypeOfService() {
		return typeOfService;
	}
	public void setTypeOfService(int typeOfService) {
		this.typeOfService = typeOfService;
	}
	public int getBookletType() {
		return bookletType;
	}
	public void setBookletType(int bookletType) {
		this.bookletType = bookletType;
	}
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}
	public LocalDate getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getRegistrationIdt() {
		return registrationIdt;
	}
	public void setRegistrationIdt(String registrationIdt) {
		this.registrationIdt = registrationIdt;
	}
	public String getStateIdt() {
		return stateIdt;
	}
	public void setStateIdt(String stateIdt) {
		this.stateIdt = stateIdt;
	}
	public String getCityIdt() {
		return cityIdt;
	}
	public void setCityIdt(String cityIdt) {
		this.cityIdt = cityIdt;
	}
	public ApplyPassport(String passId, Registration registrationId, String country, State stateId, City cityId, int pin,
			int typeOfService, int bookletType, LocalDate issueDate, LocalDate expiryDate) {
		super();
		this.passId = passId;
		this.registrationId = registrationId;
		this.country = country;
		this.stateId = stateId;
		this.cityId = cityId;
		this.pin = pin;
		this.typeOfService = typeOfService;
		this.bookletType = bookletType;
		this.issueDate = issueDate;
		this.expiryDate = expiryDate;
	}
	public ApplyPassport() {
		super();
	}
	@Override
	public String toString() {
		return "ApplyPassport [passId=" + passId + ", id=" + registrationId.getId() + ", country=" + country + ", stateId=" + stateId.getStateId()
				+ ", cityId=" + cityId.getCityId() + ", pin=" + pin + ", typeOfService=" + typeOfService + ", bookletType="
				+ bookletType + ", issueDate=" + issueDate + ", expiryDate=" + expiryDate + "]";
	}
	
	
	
}
