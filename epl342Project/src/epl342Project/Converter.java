package epl342Project;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Converter {
	private static Scanner scan=null;

	enum user {
		uid, firstName, lastName, name, link, birthday, email, website, currentWork, username, password, hometown,
		location, gender, verified, numFriends, profPic, FriendListprivacy
	}

	enum album {
		aid, fromid, name, description, location, link, privacy, count
	}

	enum privacy {
		prid, type
	}

	enum photos {
		pid, fromid, name, source, height, width, link, albumID, numOfLikes, privacy
	}

	enum videos {
		vid, fromid, name, message, link, length, description, privacy
	}

	enum links {
		lid, fromid, link, name, caption, description, message, privacy
	}

	enum events {
		eid, owner, name, description, start_time, end_time, location, venue, privacy
	}

	enum friendswith {
		uid1, uid2
	}

	enum activities {
		activityid, type
	}

	enum interestedIn {
		userIntid, actid
	}

	enum likes {
		idlikes, userid, photoid
	}

	enum commentsAlbum {
		userid, albumid, description
	}

	enum commentsVideo {
		userid, videoid, description
	}

	enum locations {
		loc_ID, city
	}

	enum works {
		workid, type
	}

	enum worksFor {
		workid, userid
	}

	enum education {
		educationid, institution
	}

	enum educatedIn {
		educationid, userid
	}

	enum quotes {
		quoteid, type
	}

	enum quotedOn {
		quoteid, userid
	}

	enum updates {
		updateid, upType, upClass, userid, date

	}

	public static void main(String[] args) {
		ArrayList<String>files=new ArrayList<>();
		// do for each text file
		for(int i=0;i<args.length;i++) {
			files.add(args[i]);
		}
		
		
	}
	
	

}
