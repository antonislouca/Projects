package epl342Project;

import java.time.LocalDateTime;
import java.util.List;


public class Link {

	int LinkID;
	int userID;
	String name;
	String description;
	String link;
	String caption;
	String message;
	Privacy privacy;


	public Link(int linkID, int userID, String name, String description, String link, String caption, String message) {
		super();
		LinkID = linkID;
		this.userID = userID;
		this.name = name;
		this.description = description;
		this.link = link;
		this.caption = caption;
		this.message = message;
	}
	
	public Link(Object[] linkRS) {
		LinkID		= (Integer) linkRS[0];
		userID		= (Integer) linkRS[1];
		name 		= (String) 	linkRS[3];
		description	= (String) 	linkRS[5];
		link 		= (String) 	linkRS[2];
		caption 	= (String) 	linkRS[4];
		message		= (String) 	linkRS[6];
		privacy= Privacy.valueOf((String) linkRS[7]);
	}



}
