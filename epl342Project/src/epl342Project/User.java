package epl342Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

enum Privacy { 
	OPEN, NETWORK, FRIEND, CLOSED;
//    0        1       2      3 
} 

public class User {

	int userID;
	String link;
	String name;
	boolean verified;
	LocalDate birthdate;
	String work;
	ArrayList<String> education;
	String email;
	String website;
	ArrayList<String> Quotes; // we have to see that
	String gender;
	String username;
	String password;
	String pPhoto;
	List<User> friends; // FIXME That's kind of difficult
	int numOffriends;
	ArrayList<String> interestedin;
	Location location; // TODO must be changed to String one (refactor)
	Location hometown; // TODO must be changes to String one (refactor)
	
	String locationStr; 
	String hometownStr; 
	
	
	Privacy privacy;
	
	

	public User(int userID, String link, String name, boolean verified, LocalDate birthdate, String work, ArrayList<String> education,
			String email, String website, ArrayList<String> quotes, String gender, String username, String password,
			List<User> friends, ArrayList<String> interestedin,String locationStr, String hometownStr,String pro) {
		super();
		this.userID = userID;
		this.link = link;
		this.name = name;
		this.verified = verified;
		this.birthdate = birthdate;
		this.work = work;
		this.education = education;
		this.email = email;
		this.website = website;
		this.Quotes = quotes;
		this.gender = gender;
		this.username = username;
		this.password = password;
		this.friends = friends;
		this.interestedin = interestedin;
		this.locationStr = locationStr;
		this.hometownStr = hometownStr;
		this.pPhoto = pro;
	}
	
	User() {}
	
	
	public User(int userID, String link, String name, boolean verified, LocalDate birthdate, String work, String education,
			String email, String website, String quotes, String gender, String username, String password,
			 Location location, Location hometown,String pro){
		
		
	}
	
	public User(int userID) {
		User u = ProfileModel.getUserInfo(userID);
		this.userID = u.userID;
		name = u.name;
		link = u.link;
		birthdate = u.birthdate;
		email = u.email;
		website = u.website;
		work = u.work;
		username = u.username;
		password = u.password;
		hometown = u.hometown;
		location = u.location;
		gender = u.gender;
		verified = u.verified;
		numOffriends = u.numOffriends;
		pPhoto = u.pPhoto;
		privacy = u.privacy;
		education = u.education;
		Quotes = u.Quotes;
		interestedin = u.interestedin;
	}
	
	public User(Object[] userRS) {
		userID 			= (Integer) 	userRS[0];
		System.out.println(userID);
		name			= (String) 		userRS[1];
		birthdate 		= ((java.sql.Date) userRS[2]).toLocalDate();
		email 			= (String)		userRS[3];
		website 		= (String)		userRS[4];
		username 		= (String)		userRS[5];
		password 		= (String)		userRS[6];
		hometownStr		= (String)		userRS[7];
		locationStr		= (String)		userRS[8];
		gender 			= ((Boolean)userRS[9])?"female":"male";
		verified 		= (Boolean) 	userRS[10];
		numOffriends 	= (Integer)		userRS[11];
		pPhoto 			= (String)		userRS[12];
		privacy			= (Privacy)		Privacy.valueOf((String) userRS[13]);
		
		work 			= ProfileModel.getUserCurrentWork(userID);
		education 		= ProfileModel.getUserEducation(userID);
		Quotes 			= ProfileModel.getUserQuotes(userID);
		interestedin 	= ProfileModel.getUserIntrests(userID);
	}

	
	
	public String toString() {
		
		return (name + " " + username + " " + hometown);
		
	}
	
	
}
