package com.pvms.model.dao.serviceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pvms.model.dao.service.RegistrationService;
import com.pvms.model.pojo.Registration;
import com.pvms.model.repository.RegistrationRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	RegistrationRepository registrationRepository;
	
	private static Map<Integer, String> map = new HashMap<Integer,String>() {{
		put(1, "jan");
		put(2, "feb");
		put(3, "mar");
		put(4, "apr");
		put(5, "may");
		put(6, "jun");
		put(7, "jul");
		put(8, "aug");
		put(9, "sep");
		put(10, "oct");
		put(11, "nov");
		put(12, "dec");
	}};
	
	@Override
	public String insertUser(Registration R) {
		String type="";
		try {
			List<Registration> list = registrationRepository.findUsersByApplyType(R.getApplyType());
			//Registration x = registrationRepository.findTopByOrderByIdDesc();
			if(!list.isEmpty()) {
				Registration x = list.get(list.size()-1);
				R.setId(generateUserId(x,R.getApplyType()));
			}else {
				R.setId(R.getApplyType()==1?"PASS-1000":"VISA-0000.1000");
			}
			
			type+=R.getApplyType()==1? "PASSPORT":"VISA";
			R.setPassword(generatePassword());
			if(R.getDob().isBefore(LocalDate.now())) R.setCitizenType(generateCitizenType(ChronoUnit.YEARS.between(R.getDob(), LocalDate.now())));
			else return "Date Should be below today";
			if(registrationRepository.findById(R.getId()).orElseGet(()->null)==null) registrationRepository.save(R);
			else return "User Already Exists";
		}catch (Exception e) {
			if(e.getMessage().contains("SYSTEM.UK_PQP6404L2NDSKPSR1XX8EAA68")) {
				return "Email Already Exists";
			}
		}
		return "Your User Id is "+R.getId()+" and your password is"+R.getPassword()+". You are planning for "+type+" and your citizen type is "+R.getCitizenType()+".";
	}
	
	public static String generateUserId(Registration last,Integer N) {
		if(N==1) {
			String x = last.getId().split("-")[1];
			Integer i = Integer.parseInt(x)+1;
			//System.out.println(i);
			return "PASS-"+i;
		}else {
			String x = last.getId().split("-")[1];
			String[] x1 = x.split("\\.");
			Integer i = Integer.parseInt(x1[1])+1;
			return "VISA-"+x1[0]+"."+i;
		}
	}
	public static String generatePassword() {
		char sp_char[] = {'@','#','$'};
		LocalDate now = LocalDate.now();
		//Generates month and gets "MMM"
		String month = map.get(now.getMonthValue());
		//gets day of month
		int i = now.getDayOfMonth();
		String day="";
		//if from 1 to 9 we add prefix 0
		if(i>0&&i<10) day+="0"+i;
		else day+=i;
		//generate random character
	    char c = sp_char[new Random().nextInt(sp_char.length)];
	    //generate random 3 digit number
	    int x = ((int)(Math.random() * 100000)) % 1000;
	    //returns everything concatenated
	    return day+month+c+x;
	}
	public static String generateCitizenType(long i) {
		
		if(i==0||i==1) return "Infant";
		else if(i>1&&i<10) return "Children";
		else if(i>10&&i<20) return "Teen";
		else if(i>20&&i<50) return "Adult";
		else return "Senior Citizen";
	}

	@Override
	public List<Registration> getAllUsers() {
		return registrationRepository.findAll();
	}

	@Override
	public Registration lastRecord() {
		return registrationRepository.findTopByOrderByIdDesc();
	}

	@Override
	public Registration getUserById(String id) {
		return registrationRepository.findById(id).orElseGet(()->null);
	}

	@Override
	public String validateUser(String username, String password) {
		username = username.trim();
		Registration R = registrationRepository.findById(username).orElseGet(()->null);
		if(R==null) return "User Not Found";
		if(R.getPassword().equals(password)) return "Valid User";
		return "Wrong Password";
	}

	@Override
	public String getContactByid(String id) {
		Registration x = registrationRepository.findById(id).orElseGet(()->null);
		if(x!=null) return x.getContactNo();
		return "0";
	}

	@Override
	public String getEmailById(String id) {
		Registration x = registrationRepository.findById(id).orElseGet(()->null);
		if(x!=null) return x.getEmail();
		return "User Not found";
	}

	@Override
	public String updatePassword(String username,String password) {
		Registration x = registrationRepository.findById(username).orElseGet(()->null);
		if(x!=null){  
			x.setPassword(password);
			registrationRepository.save(x);
			return "Password Updated Successfully!!!";
		}
		return "User Not found";
	}

	@Override
	public String updateUser(String id,Registration R) {
		Registration x = registrationRepository.findById(id).orElseGet(()->null);
		System.out.println(x);
		if(x!=null){
			if(R.getDob().isBefore(LocalDate.now())) R.setCitizenType(generateCitizenType(ChronoUnit.YEARS.between(R.getDob(), LocalDate.now())));
			else return "Date Should be below today";
			registrationRepository.save(R);
			return "Details Updated Successfully!!";
		}
		return "User not found";
	}

	@Override
	public String getHintQuestion(String id) {
		Registration R = registrationRepository.findById(id).orElseGet(()->null);
		return R==null?"User not found":R.getHintQuestion();
	}
	
	@Override
	public String getHQuestion(String id) {
		List<Registration> R = registrationRepository.findUsersByEmail(id);
		if(R.isEmpty()) return null;
		return R.get(0).getHintQuestion();
	}

	@Override
	public String validateHintAnswer(String hintAnswer, String id) {
		Registration R = registrationRepository.findById(id).orElseGet(()->null);
		
		return R==null?"User not found":R.getHintAnswer().equalsIgnoreCase(hintAnswer)?"Correct":"Incorrect";
	}
	
	@Override
	public String validateHAnswer(String hintAnswer, String id) {
		List<Registration> R = registrationRepository.findUsersByEmail(id);
		if(R.isEmpty()) return null;
		return R.get(0).getHintAnswer().equalsIgnoreCase(hintAnswer)?R.get(0).getPassword():"Incorect Hint Answer";
	}

	@Override
	public String validateEmail(String mail) {
		List<Registration> R = registrationRepository.findUsersByEmail(mail);
		//R.forEach(System.out::println);
		if(R.isEmpty())
			return "valid to use";
		return "Email Already Exists";
	}

}
