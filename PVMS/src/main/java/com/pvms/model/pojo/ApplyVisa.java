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

@Entity
@Table(name="VisaDetails")
public class ApplyVisa {
	@Id
	@Column(length=20)
	private String visaId;
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getPassid() {
		return passid;
	}
	public void setPassid(String passid) {
		this.passid = passid;
	}
	@ManyToOne
	@JoinColumn(name="id")
	private Registration registrationId;
	@Column(length=20)
	private Integer country;
	@Column(length=20)
	private Integer occupation;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfApplication;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfExpiry;
	@ManyToOne
	@JoinColumn(name="passId")
	private ApplyPassport passId;
	@Column(length=20)
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Transient
	private String regId;
	@Transient
	private String passid;
	
	@Transient
	private Integer reason;
	
	public Integer getReason() {
		return reason;
	}
	public void setReason(Integer reason) {
		this.reason = reason;
	}
	public String getVisaId() {
		return visaId;
	}
	public void setVisaId(String visaId) {
		this.visaId = visaId;
	}
	public Registration getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(Registration registrationId) {
		this.registrationId = registrationId;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}
	public Integer getOccupation() {
		return occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	public LocalDate getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(LocalDate dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public LocalDate getDateOfExpiry() {
		return dateOfExpiry;
	}
	public void setDateOfExpiry(LocalDate dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}
	public ApplyPassport getPassId() {
		return passId;
	}
	public void setPassId(ApplyPassport passId) {
		this.passId = passId;
	}
	public ApplyVisa(String visaId, Registration registrationId, Integer country, Integer occupation, LocalDate dateOfApplication,
			LocalDate dateOfExpiry, ApplyPassport passId,String status) {
		super();
		this.visaId = visaId;
		this.registrationId = registrationId;
		this.country = country;
		this.occupation = occupation;
		this.dateOfApplication = dateOfApplication;
		this.dateOfExpiry = dateOfExpiry;
		this.passId = passId;
		this.status= status;
	}
	public ApplyVisa() {
		super();
	}
	@Override
	public String toString() {
		return "ApplyVisa [visaId=" + visaId + ", registrationId=" + registrationId + ", country=" + country
				+ ", occupation=" + occupation + ", dateOfApplication=" + dateOfApplication + ", dateOfExpiry="
				+ dateOfExpiry + ", passId=" + passId + ", status=" + status + ", regId=" + regId + ", passid=" + passid
				+ ", reason=" + reason + "]";
	}
	
}
