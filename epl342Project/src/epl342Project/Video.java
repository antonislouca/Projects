package epl342Project;

import java.util.List;

public class Video {
	
	int videoID;
	int userID;
	String message;
	int length;
	String description;
	List<String> comments;
	String link;
    String name;
	Privacy privacy;
	
	public Video(int videoID, int userID, String message, float length, String description,String link, List<String> commnents) {
		super();
		this.videoID = videoID;
		this.userID = userID;
		this.message = message;
		this.length = (int) length;
		this.description = description;
		this.comments = commnents;
		this.link = link;
	}
	
	public Video(Object[] videoRS) {
		videoID		= (Integer) videoRS[0];
		userID		= (Integer) videoRS[1];
		name 		= (String) 	videoRS[2];
		message		= (String) 	videoRS[3];
		link		= (String) 	videoRS[4];
		length		= (Integer)	videoRS[5];
		description	= (String) 	videoRS[6];
		privacy		= Privacy.valueOf((String) videoRS[7]);
		comments	= ProfileModel.getVideoComments(videoID);
	}
	
	
}
