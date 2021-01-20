package epl342Project;

import java.util.List;

public class Photo {


	int photoID;
	int userID;
	String source;
	String name;
	int height;
	int width;
	String link;
	List<User> likes; // TODO must be changed to integer
	int numOflikes;
	Privacy privacy;
	int albumID = -1;
	
	
	
	public Photo(int photoID, int userID, String n, String source, int height, int width, String link, List<User> likes, Privacy p) {
		super();
		this.name = n;
		this.photoID = photoID;
		this.userID = userID;
		this.source = source;
		this.height = height;
		this.width = width;
		this.link = link;
		this.likes = likes;
		this.privacy = p;
	}
	
	
	public Photo() {
		
	}
	
	public Photo(Object[] photoRS) {
		photoID		= (Integer) photoRS[0];
		userID		= (Integer) photoRS[1];
		name		= (String) 	photoRS[2];
		source		= (String) 	photoRS[3];
		height		= (Integer) photoRS[4];
		width		= (Integer) photoRS[5];
		link		= (String) 	photoRS[6];
		albumID 	= (Integer)	photoRS[7];
		privacy 	= Privacy.valueOf((String)	photoRS[8]);
		numOflikes	= (Integer) photoRS[9];
	}
	
	public Photo(Object[] photoRS, int albumID2) {
		photoID		= (Integer) photoRS[0];
		userID		= (Integer) photoRS[1];
		name		= (String) 	photoRS[2];
		source		= (String) 	photoRS[3];
		height		= (Integer) photoRS[4];
		width		= (Integer) photoRS[5];
		link		= (String) 	photoRS[6];
		albumID 	= (Integer)	photoRS[7];
		privacy 	= Privacy.valueOf((String)	photoRS[8]);
		numOflikes	= (Integer) photoRS[9];
	}


	public String toString() {
		return String.format("%d %d %s %s %d %d %s \n",
				photoID, userID, source, name, height, width, link);
	}
	
	
	
}
