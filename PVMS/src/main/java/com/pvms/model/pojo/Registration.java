package com.pvms.model.pojo;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionId;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "registration")
public class Registration {
	@Id
	@Column(length = 14)
	private String id;
	@Column(name = "fname",length = 20)
	private String fName;
	@Column(name = "surname",length = 20)
    private String surName;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "dob",length = 10)
    private LocalDate dob;
    @Column(length = 100)
    private String address;
    @Column(length = 10)
    private String contactNo;
    @Column(length = 30,unique = true)
    private String email;
    @Column(length = 16)
    private String qualification;
    @Column(length = 10)
    private String gender;
    @Column(length = 1)
    private Integer applyType;
    @Column(name = "HintQuest",length = 50)
    private String hintQuestion;
    @Column(name = "HintAns",length = 50)
    private String hintAnswer;
    @Column(length = 9)
	private String password;
    @Column(length = 15)
    private String citizenType;
	public Registration(String id, String fName, String surName, LocalDate dob, String address, String contactNo,
			String email, String qualification, String gender, Integer applyType, String hintQuestion,
			String hintAnswer,String password,String citizenType) {
		super();
		this.id = id;
		this.fName = fName;
		this.surName = surName;
		this.dob = dob;
		this.address = address;
		this.contactNo = contactNo;
		this.email = email;
		this.qualification = qualification;
		this.gender = gender;
		this.applyType = applyType;
		this.hintQuestion = hintQuestion;
		this.hintAnswer = hintAnswer;
		this.password = password;
		this.citizenType= citizenType;
	}
	public String getCitizenType() {
		return citizenType;
	}
	public void setCitizenType(String citizenType) {
		this.citizenType = citizenType;
	}
	public Registration() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getApplyType() {
		return applyType;
	}
	public void setApplyType(Integer applyType) {
		this.applyType = applyType;
	}
	public String getHintQuestion() {
		return hintQuestion;
	}
	public void setHintQuestion(String hintQuestion) {
		this.hintQuestion = hintQuestion;
	}
	public String getHintAnswer() {
		return hintAnswer;
	}
	public void setHintAnswer(String hintAnswer) {
		this.hintAnswer = hintAnswer;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Registration [id=" + id + ", fName=" + fName + ", surName=" + surName + ", dob=" + dob + ", address="
				+ address + ", contactNo=" + contactNo + ", email=" + email + ", qualification=" + qualification
				+ ", gender=" + gender + ", applyType=" + applyType + ", hintQuestion=" + hintQuestion + ", hintAnswer="
				+ hintAnswer + ", password=" + password + ", citizenType=" + citizenType + "]";
	}

    
	
}
