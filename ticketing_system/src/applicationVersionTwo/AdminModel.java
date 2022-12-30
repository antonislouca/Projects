package applicationVersionTwo;

import java.sql.*;
import java.util.*;

public class AdminModel {

	AdminController controller;

	public AdminModel(AdminController controller) {
		this.controller = controller;
	}

	public AdminModel() {
	}

	public void setController(AdminController controller) {
		this.controller = controller;
	}

	/**
	 * The method lets a user admin be assigned to a given ticket.
	 *
	 * @param user   The user admin that will be assigned to a ticket.
	 * @param ticket The ticket that the user admin will be assigned to.
	 * @return True if the operation was successful, False otherwise.
	 */
	public static boolean assignTicketTo(User user, Ticket ticket) {
		ticket.setHandlerID(user.id);
		return assignTicketTo(user.getId(), ticket.getId());
	}

	/**
	 * The method lets a user admin be assigned to a given ticket.
	 *
	 * @param userID   The user admin id, that will be assigned to a ticket.
	 * @param ticketID The ticket id that the user admin will be assigned to.
	 * @return True if the operation was successful, False otherwise.
	 */
	public static boolean assignTicketTo(int userID, int ticketID) {
		try {
			Statement stmt = AuthenticationModel.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery("SELECT T_ASSIGNED_EMP_ID FROM TICKET WHERE T_ID = " + ticketID);

			rs.next();
			rs.updateInt("T_ASSIGNED_EMP_ID", userID);
			rs.updateRow();

			stmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * The method lets an admin remove a user from the database.
	 *
	 * @param user The user to be removed.
	 * @return True if the operation was successful, False otherwise.
	 */
	public static boolean removeUser(User user) {
		String query = "DELETE FROM EMPLOYEE WHERE EMP_ID = " + user.getId();

		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query)) {

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0)
				throw new SQLException("Deleting User failed, no rows affected.");
			if (affectedRows > 1)
				throw new SQLException("Deleting User caused more than" + "one deletion in the database.");

			stmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;

	}

	public static ArrayList<Ticket> createReport() {
		String query = 
				"SELECT T_ID, T_PRIORITY, T_CATEGORY, T_DESCRIPTION, "
				+ "T_STATUS, T_S_TIME, T_DURATION, E1.EMP_USERNAME, E2.EMP_USERNAME "
				+ "FROM TICKET, EMPLOYEE E1, EMPLOYEE E2 "
				+ "WHERE TICKET.T_EMP_ID = E1.EMP_ID AND T_STATUS = 1 "
				+ "AND TICKET.T_ASSIGNED_EMP_ID = E2.EMP_ID ";
		List<Object[]> rs = getDataFromDB(query);

		int numOfRecords = rs.size();
		Ticket[] returnedTickets = new Ticket[rs.size()];

		for (int i = 0; i < numOfRecords; i++) {

			int id = (int) rs.get(i)[0];
			String priority = (String) rs.get(i)[1];
			String category = (String) rs.get(i)[2];
			String description = (String) rs.get(i)[3];
			String status = (rs.get(i)[4] == null)? TicketStatus.toStart(): 
							(!(boolean)rs.get(i)[4])? TicketStatus.toFinish():
							TicketStatus.finished();
			status = TicketStatus.toStart();
			Timestamp startTime = (Timestamp) rs.get(i)[5];
			long duration = (long) rs.get(i)[6];
			String username = (String) rs.get(i)[7];
			String handlerUsername = (String) rs.get(i)[8];
			returnedTickets[i] = new Ticket(id, priority, category, description,
					status, startTime, duration, username, handlerUsername);
		}

		return new ArrayList<>(Arrays.asList(returnedTickets));
	}

	/**
	 * The method allows the user admin to promote another user to admin.
	 *
	 * @param user The user to be promoted to admin.
	 * @return True if the operation was successful, False otherwise.
	 */
	public static boolean promoteUserToAdmin(User user) {
		return setUserAdminStat(user, true);
	}

	/**
	 * The method allows the user admin to downgrade another admin as a simple user.
	 *
	 * @param user The user to be downgraded as simple user.
	 * @return True if the operation was successful, False otherwise.
	 */
	public static boolean RemoveUserFromAdmin(User user) {
		return setUserAdminStat(user, false);
	}

	private static boolean setUserAdminStat(User user, boolean isAdmin) {
		try {
			user.isAdmin = isAdmin;
			Statement stmt = AuthenticationModel.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stmt.executeQuery("SELECT ISADMIN FROM EMPLOYEE WHERE EMP_ID = " + user.getId());

			rs.next();
			rs.updateBoolean("ISADMIN", isAdmin);
			rs.updateRow();

			stmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	// TODO @Kon confirmRegistration One registration at a time?
	public static boolean confirmRegistration(Registration registration) {
		String query = 
				  "BEGIN TRANSACTION " 
				+ "    INSERT INTO EMPLOYEE (EMP_USERNAME, EMP_PASSWORD, "
				+ "           EMP_EMAIL, EMP_PHONE, EMP_F_NAME, EMP_L_NAME) "
				+ "    SELECT R_USERNAME, R_PASSWORD, R_EMAIL, " 
				+ "           R_PHONE, R_F_NAME, R_L_NAME "
				+ "    FROM REGISTRATION " + "    WHERE R_ID = " + registration.getId()
				+ "    DELETE FROM REGISTRATION " + "    WHERE R_ID = " + registration.getId() 
				+ "COMMIT";

		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0)
				throw new SQLException("Approving Registration failed, no rows affected.");

			try (ResultSet genKeys = stmt.getGeneratedKeys()) {
				if (genKeys.next())
					System.out.println(genKeys.getInt(1)); // TODO @Kon this is the EMP_ID of the newly registered
															// employee
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\n\n");
			e.printStackTrace();
		}
		return true;
	}

	public static ArrayList<Ticket> getTickets() { // TODO @Kon getTickets - getTickets that need to be resolved
		String query = "SELECT T_ID, T_PRIORITY, T_CATEGORY, T_DESCRIPTION, "
				+ "T_STATUS, T_S_TIME, T_DURATION, EMP_USERNAME, T_ASSIGNED_EMP_ID " + "FROM TICKET, EMPLOYEE "
				+ "WHERE TICKET.T_EMP_ID = EMPLOYEE.EMP_ID AND (T_STATUS IS NULL OR T_STATUS = 0)";
		List<Object[]> rs = getDataFromDB(query);
		if (rs == null) return new ArrayList<Ticket>();
		int numOfRecords = rs.size();
		Ticket[] returnedTickets = new Ticket[rs.size()];

		for (int i = 0; i < numOfRecords; i++) {

			int id = (int) rs.get(i)[0];
			String priority = (String) rs.get(i)[1];
			String category = (String) rs.get(i)[2];
			String description = (String) rs.get(i)[3];
			String status = (rs.get(i)[4] == null)? TicketStatus.toStart(): 
							(!(boolean)rs.get(i)[4])? TicketStatus.toFinish():
							TicketStatus.finished();
			Timestamp startTime = (Timestamp) rs.get(i)[5];
			long duration = (long) rs.get(i)[6];
			String username = (String) rs.get(i)[7];
			int handlerID = (int) rs.get(i)[8];
			returnedTickets[i] = new Ticket(id, priority, category, description, status, startTime, duration, username,
					handlerID);
		}

		return new ArrayList<>(Arrays.asList(returnedTickets));
	}

	public static FAQ[] readFAQ() {
		String query = "SELECT FAQ_ID, FAQ_Q, FAQ_A FROM FAQ";
		List<Object[]> rs = getDataFromDB(query);

		int numOfRecords = rs.size();
		FAQ[] returnedFAQs = new FAQ[rs.size()];
		for (int i = 0; i < numOfRecords; i++)
			returnedFAQs[i] = new FAQ((int) rs.get(i)[0], // FAQ id
					(String) rs.get(i)[1], // FAQ Question
					(String) rs.get(i)[2]); // FAQ Answer
		return returnedFAQs;
	}

	/**
	 * The method lets the user admin update an existing FAQ.
	 *
	 * It is assumed that the FAQ ID is correct and corresponds the the specified
	 * FAQ.
	 *
	 * @param faq The FAQ to update.
	 * @return True if the operation was successful, False otherwise.
	 */
	public static boolean updateFAQ(FAQ[] faq) {
		
		try { // Query to delete all entries
			AuthenticationModel.conn.prepareCall("{call RESET_FAQ}").execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		
		try { // Query to add new entries
			CallableStatement cstmt =
					AuthenticationModel.conn.prepareCall("{call ADD_FAQ(?,?)}");
			for (int i = 0; i < faq.length; i++) {
				cstmt.setString("Question", faq[i].getFAQ_Q());
				cstmt.setString("Answer", faq[i].getFAQ_A());
				cstmt.execute();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage()+"\n\n");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public static int executeCommandOnDB(String query) { // TODO @Kon make executeCommandOnDB private
		int genkey = -1;
		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
			stmt.executeUpdate();
			try (ResultSet genKeys = stmt.getGeneratedKeys()) {
				if (genKeys.next())
					genkey = genKeys.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\n\n");
			e.printStackTrace();
		}
		return genkey;
	}

	/**
	 * The method lets the user admin add a FAQ in the database.
	 *
	 * As soon as the FAQ has been added in the database the FAQ ID is being set to
	 * the corresponding entry.
	 *
	 * @param faq The FAQ to be added in the database.
	 * @return True if the operation was successful, False otherwise.
	 */
	public static boolean addFAQ(FAQ faq) {

		try { // Query to add new entry
			CallableStatement cstmt =
					AuthenticationModel.conn.prepareCall("{call ADD_FAQ(?,?)}");
				cstmt.setString("Question", faq.getFAQ_Q());
				cstmt.setString("Answer", faq.getFAQ_A());
				cstmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage()+"\n\n");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public static boolean addQuestion(String question, String answer) {
		// TODO check if it is needed
		return addFAQ(new FAQ(-1, question, answer));

	}

	private static List<Object[]> getDataFromDB(String query) {
		List<Object[]> results = new ArrayList<Object[]>();

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
					return null; // In case of a query UPDATE
				rs.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}

		return results;

	}

	public static User covertObjectArray(Object[] temp) {
		return new User((int) temp[0], (String) temp[1], (String) temp[2], (String) temp[3], temp[4].toString(),
				(boolean) (temp[7]), (String) temp[5], (String) temp[6]);
	}

	/**
	 * this function retrieve all the active users from the databse and returns an arraylist of all them.
	 * Stylianos
	 * @return
	 */
	public static ArrayList<User> getUsers() {

		List<Object[]> results = new ArrayList<Object[]>();

		String query = "SELECT *\r\n" + "FROM EMPLOYEE";
		results = getDataFromDB(query);
		ArrayList<User> users = new ArrayList<User>();

		for (int i = 0; i < results.size(); i++) {
			Object[] temp = results.get(i);
			users.add(covertObjectArray(temp));

		}

		return users;

	}

	public static User getUserInfo(int userID) {// Returns null if a user with that username does not exist
		List<Object[]> results = new ArrayList<Object[]>();

		String query = "SELECT *\r\n" + "FROM EMPLOYEE\r\n" + "WHERE EMP_ID =" + userID;

		results = getDataFromDB(query);
		Object[] temp = results.get(0);

		User user = covertObjectArray(temp);

		return user;

	}

///////////////////////////////////////////////////////////////
	public static boolean updateTicket(Ticket ticket) {// Here you pass the updated ticket and I using the id update the
														// 3
		// collumns
		try {
			Statement stmt = AuthenticationModel.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stmt.executeQuery("SELECT * FROM TICKET WHERE T_ID = " + ticket.getId());

			rs.next();
			rs.updateString("T_PRIORITY", ticket.getPriority());
			rs.updateString("T_CATEGORY", ticket.getCategory());
			rs.updateString("T_DESCRIPTION", ticket.getDescription());
			String stat = ticket.getStatus();
			if (stat.equals(TicketStatus.finished())) {
				rs.updateBoolean("T_STATUS", true);
				rs.updateTimestamp("T_S_TIME", ticket.getStartTime());
				rs.updateLong("T_DURATION", System.currentTimeMillis() - ticket.getStartTime().getTime());
				rs.updateInt("T_ASSIGNED_EMP_ID", ticket.getAssignedID());

			} else {
				rs.updateBoolean("T_STATUS", false);
				rs.updateTimestamp("T_S_TIME", ticket.getStartTime());
				rs.updateLong("T_DURATION", ticket.getDuration());

			}

			rs.updateRow();

			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}

	public static Registration covertObjectArrayReg(Object[] temp) {
		return new Registration((int) temp[0], (String) temp[1], (String) temp[2], Integer.toString((int) temp[4]),
				(String) temp[3], (String) temp[5], (String) temp[6]);
	}

	/**
	 * this function should retrieve all waiting to be approved users and return them as an arrayList of Users.
	 * idn what registration is. En ipame na exume mono to users je na eshi null fields?
	 * @return
	 */
	public static Registration[] getPendingRegistrations() {
		List<Object[]> results = new ArrayList<Object[]>();

		String query = "SELECT *\r\n" + "FROM REGISTRATION";
		results = getDataFromDB(query);
		Registration[] regs = new Registration[results.size()];
		for (int i = 0; i < regs.length; i++) {
			Object[] temp = results.get(i);
			regs[i] = covertObjectArrayReg(temp);
		}
		return regs;

	}

	public boolean fireUser(int userID) {
		//hope kotsios works
		return removeUser(new User(userID));
		// TODO Auto-generated method stub

	}

	public static boolean updatePolicies(String policy) {
		try {
			Statement stmt = AuthenticationModel.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stmt.executeQuery("SELECT * FROM COMP_POLICY");

			rs.next();
			rs.updateString("COMP_POLICY_TEXT", policy);

			rs.updateRow();

			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
	}

	public static String seePolicies() {
		List<Object[]> results = new ArrayList<Object[]>();

		String query = "SELECT * FROM COMP_POLICY";
		results = getDataFromDB(query);

		Object[] temp = results.get(0);

		String policy = (String) temp[1];

		return policy;

		}

	/**
	 * This function moves the user from the waiting to be approved table to the users table.
	 * returns true if successful, false otherwise
	 * @param user
	 * @return
	 */

	public static boolean approveUser(Registration user) {

		String query = "BEGIN TRANSACTION " + "    INSERT INTO EMPLOYEE (EMP_USERNAME, EMP_PASSWORD, "
				+ "           EMP_EMAIL, EMP_PHONE, EMP_F_NAME, EMP_L_NAME) "
				+ "    SELECT R_USERNAME, R_PASSWORD, R_EMAIL, " + "           R_PHONE, R_F_NAME, R_L_NAME "
				+ "    FROM REGISTRATION " + "    WHERE R_ID = " + user.getId()
				+ "    DELETE FROM REGISTRATION " + "    WHERE R_ID = " + user.getId() + "COMMIT";

		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {
			int affectedRows = stmt.executeUpdate();
//			if (affectedRows == 0)
//				throw new SQLException("Approving Registration failed, no rows affected.");

			try (ResultSet genKeys = stmt.getGeneratedKeys()) {
				if (genKeys.next())
					System.out.println(genKeys.getInt(1)); // TODO @Kon this is the EMP_ID of the newly registered
															// employee
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage() + "\n\n");
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * This function removes the user from the waiting to be approved table.
	 * returns true if successful, false otherwise
	 * @param user
	 * @return
	 */

	public static boolean disapproveUser(Registration user) {
		String query = "DELETE FROM REGISTRATION WHERE R_ID = '" + user.getId() + "'";

		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query);) {

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0)
				throw new SQLException("Deleting REGISTRATION failed, no rows affected.");
			if (affectedRows > 1)
				throw new SQLException("Deleting REGISTRATION caused more than" + "one deletion in the database.");

			stmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;



	}

	public static boolean deleteQuestion(int ID) {
		String query = "DELETE FROM FAQ WHERE FAQ_ID = " + ID;

		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query);) {

			int affectedRows = stmt.executeUpdate();
			if (affectedRows == 0)
				throw new SQLException("Deleting FAQ failed, no rows affected.");
			if (affectedRows > 1)
				throw new SQLException("Deleting FAQ caused more than" + "one deletion in the database.");

			stmt.close();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
}
