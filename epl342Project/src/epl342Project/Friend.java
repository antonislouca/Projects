package epl342Project;

import javafx.beans.property.SimpleStringProperty;

public class Friend {

	int id;
	SimpleStringProperty name;
    SimpleStringProperty birthdate;
    SimpleStringProperty email ;
    SimpleStringProperty work;
    SimpleStringProperty website;
    
  Friend(int id, String name, String birth, String em, String wor, String web) {
  	 this.id = id;
  	 this.name = new SimpleStringProperty(name);
  	 birthdate = new SimpleStringProperty(birth);
  	 email = new SimpleStringProperty(em);
  	 work = new SimpleStringProperty(wor);
  	 website = new SimpleStringProperty(web);
  	  	 
   }
  
  public String getName() {
		return name.get();
	}

	public String getBirthdate() {
		return birthdate.get();
	}

	public String getEmail() {
		return email.get();
	}

	public String getWork() {
		return work.get();
	}

	public String getWebsite() {
		return website.get();
	}
	
	
}

class FriendRequest {
	int uid1;
	int uid2;
	String name;
    String birthdate;
    String email;
    String work;
    String website;
    
    public FriendRequest(Object[] friendRequestRS) {
    	uid1 		= (Integer) friendRequestRS[0];
    	uid2 		= (Integer) friendRequestRS[1];
    	name 		= (String) 	friendRequestRS[2];
    	birthdate 	= (String) 	friendRequestRS[3];
    	email 		= (String) 	friendRequestRS[4];
    	work 		= (String) 	friendRequestRS[5];
    	website 	= (String) 	friendRequestRS[6];
    }
    
    public String toString() {
    	return String.format("%s %s %s %s %s",
    			name, birthdate, email, work, website);
    }
	
}