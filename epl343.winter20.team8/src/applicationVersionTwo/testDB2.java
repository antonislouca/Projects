package applicationVersionTwo;

import java.sql.Timestamp;

public class testDB2 {
	public static void main(String[] args) {
		System.out.println("Connection succ: " + AuthenticationModel.connectToDB());
//		 User user = AdminModel.getUserInfo(2);
//		
//		 Ticket tick = new Ticket(24, "low", "general", "testing", user.getUsername());
//		 Ticket ticket2 = new Ticket(24, "high", "general", "testing", "Resolved", new Timestamp(0), (long) 0, user.getUsername(), 1);
		System.out.println(AuthenticationModel.authenticate("kchris12", "1234"));
//		System.out.println(AuthenticationModel.register(new Registration(0, "pan", "pan", "1234", "pan", "pan", "pan")));
//		System.out.println(UserModel.addTicket(user, tick));

//		 System.out.println(UserModel.updateTicket(user, tick));
//		 System.out.println(UserModel.currentFAQS()[0]);
	
//		 System.out.println(UserModel.seePolicies());
		 
//		 System.out.println(AdminModel.getUsers());
//		 System.out.println(user);
//		 System.out.println(AdminModel.updateTicket(ticket2));
//		System.out.println(AdminModel.getPendingRegistrations()[0]);
		 
//		 System.out.println(AdminModel.updatePolicies("sfaa"));
//		 System.out.printno view thoughln(AdminModel.seePolicies());
		 
//		 Registration a = AdminModel.getPendingRegistrations()[0];
//		 System.out.println(a);
//		 User b = new User(a.getId());
//		 System.out.println(AdminModel.approveUser(b));
		 
//		 User b = new User(2);
//		 b.setUsername("tfrank05");
//		 System.out.println(AdminModel.disapproveUser(b));
		 
//		 System.out.println(AdminModel.deleteQuestion(3));
		 
 
		System.out.println("Disconnection succ: " + AuthenticationModel.disconnectToDB());
	}
	
}
