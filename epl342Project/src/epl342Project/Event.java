package epl342Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Event {

	int eventID;
	int userID; // What is this for?
	ArrayList<User> participants;
	String userName;
	String name;
	String description;
	LocalDateTime start_time;
	LocalDateTime finish_time;
	Location venue;
	Privacy p;
	String location;
	String venue2;

	public Event(int eventID, int userID, String name, String description, LocalDateTime start_time,
			LocalDateTime finish_time, String location, Privacy p, Location venue) {
		super();
		this.eventID = eventID;
	//	this.userID = userID;
		this.name = name;
		this.description = description;
		this.start_time = start_time;
		this.finish_time = finish_time;
		this.venue = venue;
		this.p = p;
		this.location = location;
	}

	public Event(Object[] eventRS) {
		eventID = (Integer) eventRS[0];
		userName = (String) eventRS[1];
		name = (String) eventRS[2];
		description = (String) eventRS[3];
		start_time = ((java.sql.Timestamp) eventRS[4]).toLocalDateTime();
		finish_time = ((java.sql.Timestamp) eventRS[5]).toLocalDateTime();
		location = (String) eventRS[6];
		venue2 = (String) eventRS[7];
		p = Privacy.valueOf((String) eventRS[8]);
	}
	
	public Event(int eventID) {
		
	}

	public String toString() {
		return eventID + " ," + userName + ", " + name + " ," + description + ", " + start_time + ", " + finish_time + ", "
				+ location + ", " + venue2 + ", " + p;
	}

}