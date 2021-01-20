package epl342Project;

public class Activity {

	
	int activityID;
	String type;
	String name;
	
	
	
	public Activity(String type, String name, int activityID) {
		super();
		this.type = type;
		this.name = name;
		this.activityID = activityID;
	}
	
	public Activity(Object[] activityRS) {
		activityID 	= (Integer) activityRS[0];
		type 		= (String)	activityRS[1];
		name 		= (String)	activityRS[2];
	}
	
	
}
