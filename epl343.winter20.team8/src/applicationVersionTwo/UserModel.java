package applicationVersionTwo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
	UserController controller;

	public UserModel(UserController controller) {
		this.controller = controller;
	}

	public UserModel() {
	}

	public void setController(UserController controller) {
		this.controller = controller;
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

	public static boolean addTicket(User user, Ticket ticket) {

		// collumns
		int count = 0;

		String query = "INSERT INTO TICKET (T_PRIORITY,T_CATEGORY,T_DESCRIPTION,T_EMP_ID)\r\n" + "VALUES (?,?,?,?)";

		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, ticket.getPriority());
			stmt.setString(2, ticket.getCategory());
			stmt.setString(3, ticket.getDescription());
			stmt.setInt(4, user.getId());

			count = stmt.executeUpdate();
			stmt.close();

		} catch (SQLException e) {
			System.out.println(e);
		}
		if (count == 1)
			return true;
		else
			return false;
	}

	public static Ticket[] currentTickets(User user) {

		String query = "SELECT *\r\n" + "FROM TICKET\r\n" + "WHERE T_EMP_ID = " + user.getId();
		List<Object[]> results = getDataFromDB(query);
		Ticket[] tick = new Ticket[results.size()];
		for (int i = 0; i < tick.length; i++) {
			Object[] temp = results.get(i);
			String status;
			
			if (temp[4] == null)
				status = TicketStatus.toStart();
			else
			if ((boolean) temp[4]) {
				status = TicketStatus.finished();
			} else {
				status = TicketStatus.toFinish();
			}

			tick[i] = new Ticket((int) temp[0], (String) temp[1], (String) temp[2], (String) temp[3], status,
					(Timestamp) temp[5], (long) temp[6], (String) user.getUsername(), (int) temp[8]);

		}

		return tick;

	}

	public static boolean updateTicket(User user, Ticket ticket) {
		// collumns

		try {
			Statement stmt = AuthenticationModel.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_UPDATABLE);

			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM TICKET WHERE T_ID = " + ticket.getId() + " AND T_EMP_ID = " + user.getId());

			rs.next();
			rs.updateString("T_PRIORITY", ticket.getPriority());
			rs.updateString("T_CATEGORY", ticket.getCategory());
			rs.updateString("T_DESCRIPTION", ticket.getDescription());
			rs.updateRow();

			stmt.close();
			return true;
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}

	}

	// PANIK AND KOTSIO PINTOSA TUTO
	public static FAQ[] currentFAQS() { // THELI ILOPIISI TUTO DEN KAMNI TIN DULIA PU PREPI , THIS NEEDS TO RETURN A
										// TABLE OF FAQ

		List<Object[]> results = new ArrayList<Object[]>();

		String query = "SELECT *\r\n" + "FROM FAQ\r\n";

		results = getDataFromDB(query);
		FAQ[] faq = new FAQ[results.size()];
		for (int i = 0; i < faq.length; i++) {
			Object[] temp = results.get(i);

			faq[i] = covertObjectArrayFAQ(temp);
		}
		return faq;

	}

	public static FAQ covertObjectArrayFAQ(Object[] temp) {
		return new FAQ((int) temp[0], (String) temp[1], (String) temp[2]);
	}

	// THIS ALSO NEEDS ILOPIIISIII
	public static String seePolicies() {
		List<Object[]> results = new ArrayList<Object[]>();

		String query = "SELECT *\r\n" + "FROM COMP_POLICY WHERE COMP_POLICY_ID = 1";
		results = getDataFromDB(query);
		Object[] temp = results.get(0);

		String policy = (String) temp[1];

		return policy;
	}
	// policies idk

}
