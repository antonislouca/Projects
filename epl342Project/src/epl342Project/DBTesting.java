package epl342Project;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class DBTesting {
	
	public static void main(String[] args) throws SQLException {
		System.out.println(AuthenticationModel.connectToDB());
		
//		String query = "EXEC User_Friends 1";
//		ArrayList<Object[]> ar = ProfileModel.getDataFromDB(query);
//		for (int i = 0; i < ar.size(); i++) {
//			for (int j = 0; j < ar.get(i).length; j++)
//				System.out.print(ar.get(i)[j]+" ");
//			System.out.println();
//		}
		
//		System.out.println(ProfileModel.searchNetPhoto("A GOOD PIC1", 3));
//		System.out.println(ProfileModel.findPopFriends(1));
		System.out.println(ProfileModel.findVideos(1));
		
//		System.out.println(ProfileModel.getFriendReq(2));
//		System.out.println(ProfileModel.acceptFriendReq(5, 96));
//		System.out.println(ProfileModel.rejectFriendReq(6, 95));
//		System.out.println(ProfileModel.ignoreFriendReq(1, 99, 1));
		
//		System.out.println(ProfileModel.getFriendList(1));
	//	System.out.println(ProfileModel.deleteFriend(1, 3));
		
		
		System.out.println(AuthenticationModel.disconnectToDB());
	}

}

class Test {
	
	
	
	public Test(Object[] testRS) {
		
	}
}