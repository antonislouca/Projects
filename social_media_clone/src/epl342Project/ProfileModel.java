package epl342Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileModel {

	ProfileController controller;

	public ProfileModel(ProfileController controller) {
		this.controller = controller;
	}

	public ProfileModel() {

	}

	public void setController(ProfileController controller) {
		this.controller = controller;
	}

	public static ArrayList<String> readWorks() {
		ArrayList<String> works = new ArrayList<>();

		String query = "SELECT type FROM GP_WORKS";
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null)
			return works;
		for (int i = 0; i < list.size(); i++)
			works.add(list.get(i)[0].toString());

		return works;
	}

	public static ArrayList<String> readEducations() {
		ArrayList<String> educations = new ArrayList<>();

		String query = "SELECT institution FROM GP_Education";
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null)
			return educations;
		for (int i = 0; i < list.size(); i++)
			educations.add(list.get(i)[0].toString());

		return educations;
	}

	public static ArrayList<String> readCities() {
		ArrayList<String> locations = new ArrayList<>();

		String query = "SELECT city FROM GP_location";
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null)
			return locations;
		for (int i = 0; i < list.size(); i++)
			locations.add(list.get(i)[0].toString());

		return locations;
	}

	public static User getUserInfo(int userID) {
		String query = "SELECT  uid AS userID, name, \r\n" + "        birthday, email, website, \r\n"
				+ "        username, password, \r\n" + "        HT.city AS userHometown, \r\n"
				+ "        LC.city AS userLocation, \r\n" + "        gender, verified, numFriends, profPic, \r\n"
				+ "        PC.type AS Privacy\r\n" + "FROM    GP_user UR,\r\n"
				+ "        GP_location HT, GP_location LC, GP_privacy PC\r\n" + "WHERE uid = " + userID
				+ "    AND HT.loc_ID = UR.hometown AND LC.loc_ID = UR.location\r\n"
				+ "    AND UR.FriendListPrivacy = PC.prid\r\n";
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null)
			return null;
		return new User(list.get(0));
	}

	public static String getUserCurrentWork(int userID) {
		String query = "SELECT WS.type as CurrentWork\r\n" + "FROM GP_user UR, GP_worksFor WF, GP_works WS\r\n"
				+ "WHERE uid = " + userID + "    AND UR.uid = WF.userid AND WF.workid = WS.workid";
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null || list.isEmpty())
			return "";
		return (String) list.get(0)[0];
	}

	public static ArrayList<String> getUserEducation(int userID) {
		ArrayList<String> userEducation = new ArrayList<>();

		String query = "SELECT institution\r\n" + "FROM GP_educatedIn ED, GP_education\r\n" + "WHERE userid = "
				+ userID;
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null)
			return userEducation;
		for (int i = 0; i < list.size(); i++)
			userEducation.add(list.get(i)[0].toString());

		return userEducation;
	}

	public static ArrayList<String> getUserQuotes(int userID) {
		ArrayList<String> userEducation = new ArrayList<>();

		String query = "SELECT type AS quote\r\n" + "FROM GP_quotedOn, GP_quotes\r\n" + "WHERE userid = " + userID;
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null)
			return userEducation;
		for (int i = 0; i < list.size(); i++)
			userEducation.add(list.get(i)[0].toString());

		return userEducation;
	}

	public static ArrayList<String> getUserIntrests(int userID) {
		ArrayList<String> userEducation = new ArrayList<>();

		String query = "SELECT type AS Interest\r\n" + "FROM GP_activities, GP_interestedin\r\n" + "WHERE userIntid = "
				+ userID;
		ArrayList<Object[]> list = getDataFromDB(query);
		if (list == null)
			return userEducation;
		for (int i = 0; i < list.size(); i++)
			userEducation.add(list.get(i)[0].toString());

		return userEducation;
	}

	public static boolean sendFriendReq(int userID, int friendID) {
		String call = "{call insertFriendReq(?, ?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = AuthenticationModel.conn.prepareCall(call);
			cstmt.setInt(1, userID);
			cstmt.setInt(2, friendID);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static ArrayList<String> addFriend() {

		return null;
	}

	public static ArrayList<String> rejectFriendReq() {

		return null;
	}

	public static ArrayList<String> ignoreFriendReq() {

		return null;
	}

//	public static ArrayList<String> getUpdates() {
//
//		return null;
//	}

	public static ArrayList<Friend> getFriendsList(int userID) { // TODO
		ArrayList<Friend> userFriedList = new ArrayList<>();
//		
//		String query = "";
//		ArrayList<Object[]> rs = getDataFromDB(query);
//		
//		int numOfRecords = rs.size();
//		for (int i = 0; i < numOfRecords; i++)
//			userFriedList.add(new Friend(rs.get(i)));

		return userFriedList;
	}


	// OK
	public static ArrayList<Album> getAlbumFromName(String name, int userID) throws SQLException {

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserAlbums ('%s', %d)}", name, userID));
		ArrayList<Album> results = new ArrayList<>();

		if (rs == null || rs.isEmpty()) return null;
			results.add(new Album(rs.get(0)));

		return results;

	}
	
	
	public static ArrayList<Album> findAlbum(int userID) throws SQLException {

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call findAlbums (%d)}", userID));
		ArrayList<Album> results = new ArrayList<>();
		
		//System.out.println(userID);

		if (rs == null || rs.isEmpty()) return null;
			
		for (int i = 0; i < rs.size(); i++) {
			results.add(new Album(rs.get(i)));
		}
		
		return results;

	}
	

	public static ArrayList<Photo> getUserPhotos(String name, int userID) { 
		ArrayList<Photo> photos = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserPhotos ('%s', %d)}", name, userID));
		// String query = "SELECT * FROM GP_PHOTOS";
		if (rs == null || rs.isEmpty()) return null;
			photos.add(new Photo(rs.get(0)));
//		int numOfRecords = rs.size();
//		for (int i = 0; i < numOfRecords; i++)
//			photos.add(new Photo(rs.get(i)));

		return photos;
	}
	
	public static ArrayList<Photo> findPhotos(int userID) { 
		ArrayList<Photo> photos = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call findPhotos (%d)}",userID));
		// String query = "SELECT * FROM GP_PHOTOS";
		if (rs == null || rs.isEmpty()) return null;
		
		for (int i = 0; i < rs.size(); i++) {
			photos.add(new Photo(rs.get(i)));
		}

		return photos;
	}
	
	

	public static ArrayList<Video> FindUserVideos(String name, int userID) {
		ArrayList<Video> vid = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserVideos ('%s', %d)}", name, userID));

//		for (int i = 0; i < rs.size(); i++) {
//			vid.add(new Video(rs.get(i)));
//		}
		
		if (rs == null || rs.isEmpty()) return null;

		vid.add(new Video(rs.get(0)));

		return vid;

	}
	
	

	public static ArrayList<Video> findVideos(int userID) {
		ArrayList<Video> vid = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call findVideos (%d)}",userID));
		
		System.out.println(rs);
		
		if (rs == null || rs.isEmpty()) return null;
		
		for (int i = 0; i < rs.size(); i++) {
			vid.add(new Video(rs.get(i)));
		}

		return vid;

	}

	public static ArrayList<Link> FindUserLinks(String name, int userID) {
		ArrayList<Link> links = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserVideos ('%s',%d)}", name, userID));

//		for (int i = 0; i < rs.size(); i++) {
//			links.add(new Link(rs.get(i)));
//		}

		if (rs == null || rs.isEmpty()) return null;
			links.add(new Link(rs.get(0)));
		return links;
	}
	
	public static ArrayList<Link> findLinks(int userID) {
		ArrayList<Link> links = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call findLinks (%d)}",userID));

		for (int i = 0; i < rs.size(); i++) {
			links.add(new Link(rs.get(i)));
		}

		if (rs == null || rs.isEmpty()) return null;
			//links.add(new Link(rs.get(0)));
		
		return links;
	}
	
	

	public static ArrayList<Event> findUserEvents(String name, int userID) {
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserEvents ('%s',%d)}", name, userID));
		ArrayList<Event> events = new ArrayList<>();

		if (rs == null || rs.isEmpty()) return null;
		
			events.add(new Event(rs.get(0)));

//		for (int i = 0; i < rs.size(); i++) {
//			events.add(new Event(rs.get(i)));
//		}

		return events;

	}
	
	
	public static ArrayList<Event> findEvents(int userID) {
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call findEvents (%d)}", userID));
		ArrayList<Event> events = new ArrayList<>();

		if (rs == null || rs.isEmpty()) return null;
//			events.add(new Event(rs.get(0)));

		for (int i = 0; i < rs.size(); i++) {
			events.add(new Event(rs.get(i)));
		}

		return events;

	}
	

	// THIS IS AN EXTRA FEATURE THUS I DIDNT WRITE ANYTHING IN BECAUSE OTHERS
	// ARE MORE IMPORTANT RIGHT NOW
	public static ArrayList<Album> findAlbumsBasedOnLocation(String loc) {
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserAlbumsbasedonLocation ('%s')}", loc));
		return null;
	}

	// THIS IS AN EXTRA FEATURE THUS I DIDNT WRITE ANYTHING IN BECAUSE OTHERS
	// ARE MORE IMPORTANT RIGHT NOW
	public static ArrayList<Album> FindAlbumsbasedonDescription(String desc) {
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserAlbumsbasedonDescription ('%s')}", desc));
		return null;
	}

	// THIS IS AN EXTRA FEATURE THUS I DIDNT WRITE ANYTHING IN BECAUSE OTHERS
	// ARE MORE IMPORTANT RIGHT NOW
	public static ArrayList<Album> spFindUserVideosWithDesc(String desc) {
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserVideosWithDesc ('%s')}", desc));
		return null;
	}

	// THIS IS AN EXTRA FEATURE THUS I DIDNT WRITE ANYTHING IN BECAUSE OTHERS
	// ARE MORE IMPORTANT RIGHT NOW
	public static ArrayList<Album> spFindUserVideosWithLen(int len) {
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserVideosWithLen ('%s')}", len));
		return null;
	}

	// THIS IS AN EXTRA FEATURE THUS I DIDNT WRITE ANYTHING IN BECAUSE OTHERS
	// ARE MORE IMPORTANT RIGHT NOW
	public static ArrayList<Album> spFindUserEventsOnvenue(String ven) {
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUserEventsOnvenue ('%s')}", ven));
		return null;
	}

	public static ArrayList<User> SimpleSearchUser( String Name, String date, String Loc,int function) {
		// the integer function determines which stored procedure will be called
		// 1:name only,2:birhtday,3:location,4: name&date,5:name&loc,6:loc&Date,7:all
		// three of them
		// STARTED CHANGING IN THE QUERY BUT WE HAVE TO FIND LOCATION AS STRING AND
		// NOT INT IDK
		
		ArrayList<User> users = new ArrayList<>();

		ArrayList<Object[]> list = getDataFromDB(String.format("{call spSearchALL "
				+ "('%s','%s','%s',%d)}", Name,date,Loc,function));

		if (list == null) return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;

	}

	public static ArrayList<User> ComplexSearchUser(int function, String arguments) {
		ArrayList<User> users = new ArrayList<>();
		// o:for education,1:for works NOTE THE FILED IN SP IS BIT TYPE
		// it reminds me of a c function
		ArrayList<Object[]> list = getDataFromDB(String.format("{call spComplexSearch (%d,'%s')}", function, arguments));
		if (list == null) return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
		
		

	}
	// u: average net size
	public static int  getAverageage (int userid) {
		
		CallableStatement cstmt = null;
		
			ArrayList<Object[]> list = getDataFromDB(String.format("{call findaverageAge (%d)}", userid));
//	cstmt = AuthenticationModel.conn.prepareCall("{call findaverageAge(?)}");
//	cstmt.setInt(1, userid);
//	cstmt.execute();
		//	System.out.println(list.get(0)[0]);
			int  value =(int)list.get(0)[0];
			return value;
			
			
	}
	

	public static ArrayList<Updates> getAllUpdates(int levels, int uid) {
		ArrayList<Updates> up = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUpdatesGlobal (%d,%d)}", levels, uid));
		// System.out.println(rs.size());
		
		if (rs == null || rs.isEmpty()) return null;

		for (int i = 0; i < rs.size(); i++) {
			up.add(new Updates(rs.get(i)));
		}

		return up;
	}

	public static ArrayList<Updates> spFindUpdatesEvents(int levels, int uid) {
		ArrayList<Updates> up = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUpdatesEvents (%d,%d)}", levels, uid));
		// System.out.println(rs.size());
		if (rs == null || rs.isEmpty()) return null;

		for (int i = 0; i < rs.size(); i++) {
			up.add(new Updates(rs.get(i)));
		}

		return up;
	}

	public static ArrayList<Updates> spFindUpdatesLinks(int levels, int uid) {
		ArrayList<Updates> up = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUpdatesLinks (%d,%d)}", levels, uid));
		// System.out.println(rs.size());
		if (rs == null || rs.isEmpty()) return null;

		for (int i = 0; i < rs.size(); i++) {
			up.add(new Updates(rs.get(i)));
		}

		return up;
	}

	public static ArrayList<Updates> spFindUpdatesVideos(int levels, int uid) {
		ArrayList<Updates> up = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUpdatesVideos (%d,%d)}", levels, uid));
		// System.out.println(rs.size());
		if (rs == null || rs.isEmpty()) return null;

		for (int i = 0; i < rs.size(); i++) {
			up.add(new Updates(rs.get(i)));
		}

		return up;
	}

	public static ArrayList<Updates> spFindUpdatesPhotos(int levels, int uid) {
		ArrayList<Updates> up = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUpdatesPhotos (%d,%d)}", levels, uid));
		// System.out.println(rs.size());
		if (rs == null || rs.isEmpty()) return null;

		for (int i = 0; i < rs.size(); i++) {
			up.add(new Updates(rs.get(i)));
		}

		return up;
	}

	public static ArrayList<Updates> spFindUpdatesAlbum(int levels, int uid) {
		ArrayList<Updates> up = new ArrayList<>();

		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spFindUpdatesAlbums (%d,%d)}", levels, uid));
		// System.out.println(rs.size());
		if (rs == null || rs.isEmpty()) return null;

		for (int i = 0; i < rs.size(); i++) {
			up.add(new Updates(rs.get(i)));
		}

		return up;
	}

	public static ArrayList<Event> spShowEventsName(int userid, String name) {
		ArrayList<Event> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spShowEventsName (%d,'%s')}", userid, name));

		if (rs == null || rs.isEmpty()) return null;
		
		for (int i = 0; i < rs.size(); i++) {
			ev.add(new Event(rs.get(i)));
		}
		return ev;
	}

	public static ArrayList<Event> spShowEventsVenue(int userid, String name) {
		ArrayList<Event> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spShowEventsVenue (%d,'%s')}", userid, name));
		
		if (rs == null || rs.isEmpty()) return null;
		
		for (int i = 0; i < rs.size(); i++) {
			ev.add(new Event(rs.get(i)));
		}
		return ev;
	}

	public static void spMarkParticipation(int userid, int id) {

		try {
			PreparedStatement stmt = AuthenticationModel.conn
					.prepareStatement(String.format("{call spMarkParticipation (%d,%d)}", userid, id));
			stmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<Photo> spShowPhotos(int userid, String name) {
		ArrayList<Photo> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spShowPhotos ('%s',%d)}", name, userid));
		
		if (rs == null || rs.isEmpty()) return null;
		
		for (int i = 0; i < rs.size(); i++) {
			ev.add(new Photo(rs.get(i)));
		}
		return ev;
	}

	public static ArrayList<Video> spShowVideos(int userid, String name) {
		ArrayList<Video> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spShowVideos ('%s',%d)}", name, userid));
		
		if (rs == null || rs.isEmpty()) return null;
		
		for (int i = 0; i < rs.size(); i++) {
			ev.add(new Video(rs.get(i)));
		}
		return ev;
	}

	public static ArrayList<Link> spShowLinks(int userid, String name) {
		ArrayList<Link> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spShowLinks ('%s',%d)}", name, userid));

		
		if (rs == null || rs.isEmpty()) return null;
		for (int i = 0; i < rs.size(); i++) {
			ev.add(new Link(rs.get(i)));
		}
		return ev;
	}

	public static List<Album> spShowAlbums(int userid, String albumname) {
		ArrayList<Album> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spShowAlbums (%d,'%s')}", userid, albumname));
		if (rs == null || rs.isEmpty()) return null;
		for (int i = 0; i < rs.size(); i++) {
			ev.add(new Album(rs.get(i)));
		}
		return ev;

	}

	public static List<Photo> getAlbumPhotos(int albumID) {
		ArrayList<Photo> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call getPhotosOFAlbum (%d)}", albumID));
		if (rs == null || rs.isEmpty()) return null;
		for (int i = 0; i < rs.size(); i++) {
			ev.add(new Photo(rs.get(i)));
		}
		return ev;
	}

	public static ArrayList<String> getAlbumComments(int albumID) {
		ArrayList<String> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spGetAlbumComments (%d)}", albumID));
		if (rs == null || rs.isEmpty()) return null;
		for (int i = 0; i < rs.size(); i++) {
			ev.add(rs.get(i).toString());
		}
		return ev;
	}

	public static ArrayList<String> getVideoComments(int vidid) {
		ArrayList<String> ev = new ArrayList<>();
		ArrayList<Object[]> rs = getDataFromDB(String.format("{call spGetAlbumComments (%d)}", vidid));
		if (rs == null || rs.isEmpty()) return null;
		for (int i = 0; i < rs.size(); i++) {
			ev.add(rs.get(i).toString());
		}
		return ev;
	}

	public static String getlocFromID(Integer integer) {
		// String r = null;
			String query = String.format("EXEC spGetLocFromID %d", integer);
			ArrayList<Object[]> list = getDataFromDB(query);
			if (list == null || list.isEmpty()) return null;
			return (String) list.get(0)[0];

	}

//	public static int getPhotosAlbum() { // like come on we only need 1 id, name and count
//											// a whole obj is too much
//		return -1;
//	}

//	public static ArrayList<String> getVideos(int videoID) {
//		
//		return null;
//	}
//	public static ArrayList<String> getLink() {
//		
//		return null;
//	}
//	public static ArrayList<String> getLink(int linkID) {
//		
//		return null;
//	}
	
	public static boolean insertFriendReq(int userID, int friendID) {
		String call = "{call insertFriendReq(?, ?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = AuthenticationModel.conn.prepareCall(call);
			cstmt.setInt(1, userID);
			cstmt.setInt(2, friendID);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// g1/4: Manage friend requests (show friend req)
	public static ArrayList<User> getFriendReq(int userID) {
		ArrayList<User> users = new ArrayList<>();

		String query = String.format("EXEC spShowFriendReq %d", userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
	}
	
	
	public static ArrayList<User> getIgnoredFriendReq(int userID) {
		ArrayList<User> users = new ArrayList<>();

		String query = String.format("EXEC spShowIgnoredFriendReq %d", userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		
		if (list == null)
			return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
	}
	
	
	

	// g2/4: Manage friend requests (accept friend req)
	public static boolean acceptFriendReq(int userID, int friendID) {
		String call = "{call spAcceptFriendReq(?, ?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = AuthenticationModel.conn.prepareCall(call);
			cstmt.setInt(1, userID);
			cstmt.setInt(2, friendID);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// g3/4: Manage friend requests (reject friend req)
	public static boolean rejectFriendReq(int userID, int friendID) {
		String call = "{call spRejectFriendReq(?, ?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = AuthenticationModel.conn.prepareCall(call);
			cstmt.setInt(1, userID);
			cstmt.setInt(2, friendID);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	// g4/4: Manage friend requests (ignore friend req)
	public static boolean ignoreFriendReq(int userID, int friendID, int ignored) {
		String call = "{call spIgnoreFriendReq(?, ?, ?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = AuthenticationModel.conn.prepareCall(call);
			cstmt.setInt(1, userID);
			cstmt.setInt(2, friendID);
			cstmt.setInt(3, ignored);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	
	}
	
	// h1/2: Manage friends - View 
	public static ArrayList<User> getFriendList(int userID) {
		ArrayList<User> userFriends = new ArrayList<>();

		String query = String.format("EXEC User_Friends %d", userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null) return userFriends;
		for (int i = 0; i < list.size(); i++)
			userFriends.add(new User((int) list.get(i)[0]));

		return userFriends;
	}
	// h2/2 Manage friends - View 
	public static boolean deleteFriend(int userID, int friendID ) {
		String call = "{call spDeleteFriend(?, ?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = AuthenticationModel.conn.prepareCall(call);
			cstmt.setInt(1, userID);
			cstmt.setInt(2, friendID);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static ArrayList<Photo> searchUserPhoto(String photoName, int userID) {
		ArrayList<Photo> photos = new ArrayList<>();

		String query = String.format("EXEC spFindUserPhotos '%s', %d", photoName, userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return photos;
		for (int i = 0; i < list.size(); i++)
			photos.add(new Photo(list.get(i)));

		return photos;

	}

	public static ArrayList<Photo> searchNetPhoto(String photoName, int userID) {
		ArrayList<Photo> photos = new ArrayList<>();

		String query = String.format("EXEC spShowPhotos '%s', %d", photoName, userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return photos;
		for (int i = 0; i < list.size(); i++)
			photos.add(new Photo(list.get(i)));

		return photos;

	}

	// o: Find pop friend
	public static ArrayList<User> findPopFriends(int userID) {
		ArrayList<User> users = new ArrayList<>();

		String query = String.format("EXEC spFindPoPFriends %d", userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
	}

	// p: find common friends
	public static ArrayList<User> findCommonFriends(int userID) {
		ArrayList<User> users = new ArrayList<>();

		String query = String.format("EXEC spFindCommonFriends %d", userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
	}

	// q: Find users with greatest albums
	public static ArrayList<User> findFriendsWithLargestAlbums(int userID, int X) {
		ArrayList<User> users = new ArrayList<>();

		String query = String.format("EXEC spFindFriendsWithLargestAlbums %d, %d", userID, X);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
	}

	// r: Find users with greatest albums
	public static ArrayList<User> findUsersWithLargestAlbums(int userID, int X) {
		ArrayList<User> users = new ArrayList<>();

		String query = String.format("EXEC spFindNetFriendsWithLargestAlbums %d, %d", userID, X);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
	}

	// s: exactly the same interests Search
	public static ArrayList<User> sameIntrestsSearch(int userID) {
		ArrayList<User> users = new ArrayList<>();

		String query = String.format("EXEC spFindFriendsWithCommonIntrests %d", userID);
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null)
			return users;
		for (int i = 0; i < list.size(); i++)
			users.add(new User((int) list.get(i)[0]));

		return users;
	}
	
	// t: non-pop events
	public static ArrayList<Event> nonPopEvents(){
		ArrayList<Event> events = new ArrayList<>();

		String query = String.format("EXEC findLeastFamousEvents");
		ArrayList<Object[]> list = getDataFromDB(query);

		if (list == null) return events;
		for (int i = 0; i < list.size(); i++)
			events.add(new Event(list.get(i)));

		return events;
	
		
	}
	
	
	public static boolean editPersonalInfo(int userID, 
			String username, String password, String firstName, 
			String lastName, String birthday, String email, 
			String work, String edu, String web, String loc, String home) {
		String call = "{call updateUser(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = AuthenticationModel.conn.prepareCall(call);
			cstmt.setInt(1, userID);
			cstmt.setString(2, username);
			cstmt.setString(3, password);
			cstmt.setString(4, firstName);
			cstmt.setString(5, lastName);
			cstmt.setDate(6, java.sql.Date.valueOf(birthday));
			cstmt.setString(7, email);
			cstmt.setString(8, work);
			cstmt.setString(9, edu);
			cstmt.setString(10, web);
			cstmt.setString(11, home);
			cstmt.setString(12, loc);
			cstmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	
	}

	public static ArrayList<Object[]> getDataFromDB(String query) {
		ArrayList<Object[]> results = new ArrayList<Object[]>();

		ResultSet rs = null;

		try {
			PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query);
			rs = stmt.executeQuery();
			int cols = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] attributes = new Object[cols];
				for (int i = 0; i < cols; i++)
					attributes[i] = rs.getObject(i + 1);
				results.add(attributes); // many attributes make a record

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs == null)
					return null; // no results returned
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return results;
	}

}
