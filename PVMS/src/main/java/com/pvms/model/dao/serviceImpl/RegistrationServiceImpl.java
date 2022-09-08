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
	
	//Using this map we get MMM format of the month for number we got using LocalDate.getMonthValue() method which we use in password generation
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
			//We get users list based on application type 
			List<Registration> list = registrationRepository.findUsersByApplyType(R.getApplyType());
			//If list is not empty we need to fetch last record from database add one to the new user andd insert it.
			if(!list.isEmpty()) {
				Integer large=0;
				if(R.getApplyType()==1) {
					//Fetching the maximum value of last four digits if application type is PASSPORT.
					large = list.stream().map(x->Integer.parseInt(x.getId().split("-")[1])).reduce(0,(a,b)->Integer.max(a,b));
				}else {
					//Fetching the maximum value of last four digits if application type is VISA.
					large = list.stream().map(x->Integer.parseInt(x.getId().split("\\.")[1])).reduce(0,(a,b)->Integer.max(a,b));
				}
				//Assigning generated Id to Id.
				R.setId(generateUserId(large,R.getApplyType()));
			}else {
				//If we do not have any records in database we put the initial values.
				R.setId(R.getApplyType()==1?"PASS-1000":"VISA-0000.1000");
			}
			//Assigning the variable type as PASSPORT or VISA.
			type+=R.getApplyType()==1? "PASSPORT":"VISA";
			//Assigning generated Password to Password.
			R.setPassword(generatePassword());
			//checking the condition that user should enter date before today and assigning citizen type based on DOB.
			if(R.getDob().isBefore(LocalDate.now())) R.setCitizenType(generateCitizenType(ChronoUnit.YEARS.between(R.getDob(), LocalDate.now())));
			//Displaying the error that date should be below today.
			else return "Date Should be below today";
			//If record not found in database we save the record to the database with generated id, if user found will return error message.
			if(registrationRepository.findById(R.getId()).orElseGet(()->null)==null) {
				registrationRepository.save(R);
				//On successful insertion of user returns the message .
				return "Your User Id is "+R.getId()+" and your password is"+R.getPassword()+". You are planning for "+type+" and your citizen type is "+R.getCitizenType()+".";
			}
			else return "User Already Exists";
		}catch (Exception e) {
			//Email should be unique. This method will check for uniqueness of email.
			if(e.getMessage().contains("SYSTEM.UK_PQP6404L2NDSKPSR1XX8EAA68")) {
				return "Email Already Exists";
			}
			//Returns error if anything went wrong.
			return e.getMessage();
		}
				
	}
	
	//generation of user id based on apply type
	public static String generateUserId(Integer last,Integer N) {
		Integer i = last+1;
		return N==1?"PASS-"+i:"VISA-0000."+i;
	}
	//generation of password
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
	//it will generate the citizen type of user.
	public static String generateCitizenType(long i) {
		
		if(i==0||i==1) return "Infant";
		else if(i>1&&i<10) return "Children";
		else if(i>10&&i<20) return "Teen";
		else if(i>20&&i<50) return "Adult";
		else return "Senior Citizen";
	}

	//returns all the users from database.
	@Override
	public List<Registration> getAllUsers() {
		return registrationRepository.findAll();
	}

	//gets user based on the id(primary key).
	@Override
	public Registration getUserById(String id) {
		return registrationRepository.findById(id).orElseGet(()->null);
	}

	//checks whether user is valid or not. 
	@Override
	public String validateUser(String username, String password) {
		username = username.trim();
		Registration R = registrationRepository.findById(username).orElseGet(()->null);
		if(R==null) return "User Not Found";
		if(R.getPassword().equals(password)) return "Valid User";
		return "Wrong Password";
	}

	//fetches contact number based on the user id.
	@Override
	public String getContactByid(String id) {
		Registration x = registrationRepository.findById(id).orElseGet(()->null);
		if(x!=null) return x.getContactNo();
		return "0";
	}

	//fetches mail using user id.
	@Override
	public String getEmailById(String id) {
		Registration x = registrationRepository.findById(id).orElseGet(()->null);
		if(x!=null) return x.getEmail();
		return "User Not found";
	}

	//it will update the password, used for changing password.
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

	//used to update the user details.
	@Override
	public String updateUser(String id,Registration R) {
		Registration x = registrationRepository.findById(id).orElseGet(()->null);
		if(x!=null){
			//checks for the date of birth and assigns citizen type based on date of birth.
			if(R.getDob().isBefore(LocalDate.now())) R.setCitizenType(generateCitizenType(ChronoUnit.YEARS.between(R.getDob(), LocalDate.now())));
			else return "Date Should be below today";
			registrationRepository.save(R);
			return "Details Updated Successfully!!";
		}
		return "User not found";
	}

	//get hint question based on user id
	@Override
	public String getHintQuestion(String id) {
		Registration R = registrationRepository.findById(id).orElseGet(()->null);
		return R==null?"User not found":R.getHintQuestion();
	}
	
	//get hint question based on user email
	@Override
	public String getHQuestion(String id) {
		List<Registration> R = registrationRepository.findUsersByEmail(id);
		if(R.isEmpty()) return null;
		return R.get(0).getHintQuestion();
	}

	//validates hint answer based on user id
	@Override
	public String validateHintAnswer(String hintAnswer, String id) {
		Registration R = registrationRepository.findById(id).orElseGet(()->null);
		
		return R==null?"User not found":R.getHintAnswer().equalsIgnoreCase(hintAnswer)?"Correct":"Incorrect";
	}

	//validates hint answer based on user email
	@Override
	public String validateHAnswer(String hintAnswer, String id) {
		List<Registration> R = registrationRepository.findUsersByEmail(id);
		if(R.isEmpty()) return null;
		return R.get(0).getHintAnswer().equalsIgnoreCase(hintAnswer)?R.get(0).getPassword():"Incorect Hint Answer";
	}

	//validate email if already present in dB returns email already exists.
	@Override
	public String validateEmail(String mail) {
		List<Registration> R = registrationRepository.findUsersByEmail(mail);
		if(R.isEmpty())
			return "valid to use";
		return "Email Already Exists";
	}

}
