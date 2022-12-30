package applicationVersionTwo;

import java.util.ArrayList;
import java.util.Arrays;

public class DBTesting {

	public static void main(String[] args) {
		System.out.println("Connection succ: " + AuthenticationModel.connectToDB());
		
//		AdminModel.assignTicketTo(1, 1);
//		System.out.println(AdminModel.confirmRegistration(new Registration(1, "m","f","f","f","f","f")));
		System.out.println(AdminModel.createReport());
		
		
		System.out.println("Disconnection succ: " + AuthenticationModel.disconnectToDB());
	}

}
