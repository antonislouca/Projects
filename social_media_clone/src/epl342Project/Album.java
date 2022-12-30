package epl342Project;

import java.util.Date;
import java.util.List;

public class Album {

	int albumID;
	int userID;
	String name;
	String description;
	String link;
	Privacy p;
	int count; // we have to see that
	List<Photo> photos;
	List<String> comments;
	//Location location;
	String city;
	
	
	public Album(int albumID, int userID, String name, String description, String link, Privacy p, int count,
			List<Photo> photos, List<String> commnents, Location location) {
		super();
		this.albumID = albumID;
		this.userID = userID;
		this.name = name;
		this.description = description;
		this.link = link;
		this.p = p;
		this.count = count;
		this.photos = photos;
		this.comments = commnents;
		//this.location = location;
	}
	
	public Album(Object[] albumRS) {
		albumID		= (Integer) albumRS[0];
		userID		= (Integer) albumRS[1];
		
		name 		= (String) 	albumRS[2];
		description	= (String) 	albumRS[3];
		city 		= ProfileModel.getlocFromID((Integer) albumRS[4]);
		link 		= (String) 	albumRS[5];
		p 			= Privacy.valueOf((String) albumRS[6]);//System.out.println(p);
		count 		= (Integer)	albumRS[7];
//		photos 		= (String) 	albumRS[7];
//		commnents 	= (String) 	albumRS[8];
		photos=ProfileModel.getAlbumPhotos(albumID);
		comments=ProfileModel.getAlbumComments(albumID);
	}



	
}
