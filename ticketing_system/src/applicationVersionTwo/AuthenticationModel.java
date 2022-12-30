package applicationVersionTwo;

import java.sql.*;
import java.util.*;

public class AuthenticationModel {
	AuthenticationController controller;
	
	public AuthenticationModel(AuthenticationController controller) {
		this.controller= controller;
	}
	public AuthenticationModel() {
	}
	public void setController(AuthenticationController controller) {
		this.controller=controller;
	}

	static String dbConnString = "jdbc:sqlserver://mssql.cs.ucy.ac.cy;user=kchris12;password=7m3aPvBA;";

	static Connection conn = null;
	private static boolean dbDriverLoaded = false;

	public static boolean connectToDB() {

		if (!dbDriverLoaded)
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				dbDriverLoaded = true;
			} catch (ClassNotFoundException e) {
				System.out.println("Cannot load DB driver!");
				return false;
			}

		try {
			if (conn == null) {
				conn = DriverManager.getConnection(dbConnString);
				conn.setAutoCommit(true);
				conn.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
			}
			else if (conn.isClosed()) {
				conn = DriverManager.getConnection(dbConnString);
				conn.setAutoCommit(true);
				conn.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
			}
			return true;
		} catch (SQLException e) {
			System.out.print("Cannot connect to the DB!\nGot error: ");
			System.out.print(e.getErrorCode());
			System.out.print("\nSQL State: ");
			System.out.println(e.getSQLState());
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public static boolean disconnectToDB() {
		try {
			if (!conn.isClosed()) {
				System.out.print("Disconnecting from database...");
				conn.close();
				System.out.println("Done\n\nBye !");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 *TODO
	 * This function return the user if there is a user with that username and his password is the given one. 
	 * If there is no such user or the password is wrong then it returns null;
	 * If the credentials are correct then it returns a User object with the values of the entry of the table as the values
	 * of the User fields. 
	 * @param username
	 * @param password
	 * @return User user.
	 *Stylianos
	 */
	public static User authenticate(String username, String password) {
		/* this function should return a check if the username exists and if he exists in the database
		 * and 
		 */
		List<Object[]> results = new ArrayList<Object[]>();
		ResultSet rs = null;

		try (Statement stmt = AuthenticationModel.conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);) {

			String query = "SELECT *\r\n" + "FROM EMPLOYEE\r\n" + "WHERE EMP_USERNAME ='" + username
					+ "' AND EMP_PASSWORD ='" + password + "'";
			rs = stmt.executeQuery(query);
			if (rs.next() == false) {
				return null;
			} else {
				int cols = rs.getMetaData().getColumnCount();
				Object[] arr = new Object[cols];
				for (int i = 0; i < cols; i++) {
					Object obj = rs.getObject(i + 1);
					arr[i] = obj;
				}
				results.add(arr);
			}
		} catch (SQLException e) {
			return null;
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
				return null;
			}
		}

		Object[] temp = results.get(0);
		return AdminModel.covertObjectArray(temp);

	}
//	// columns
//	int count = 0;
//
//	String query = "INSERT INTO REGISTRATION (R_USERNAME,R_PASSWORD,R_PHONE,R_EMAIL,R_F_NAME,R_L_NAME)\r\n"
//			+ "VALUES (' "+""+" ',"
//			+ "' "+registration.getUsername()+" ' ,"
//			+ "' "+registration.getPassword()+" ' ,"
//			+ "' "+registration.getPhone()+" ' ,"
//			+ "' "+registration.getEmail()+" ' ,"
//			+ "' "+registration.getL_Name()+" ')";
//
//	try (Statement stmt = AuthenticationModel.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
//		stmt.setString(1, registration.getUsername());
//		stmt.setString(2, registration.getPassword());
//		stmt.setString(3, registration.getPhone());
//		stmt.setString(4, registration.getEmail());
//		stmt.setString(5, registration.getF_Name());
//		stmt.setString(6, registration.getL_Name());
//
//		count = stmt.executeUpdate(query);
//		stmt.close();
//		
//	} catch (SQLException e) {
//		System.out.println(e);
//		return false;
//	}
	public static boolean register(Registration registration) {
		// columns
		int count = 0;

		String query = "INSERT INTO REGISTRATION (R_USERNAME,R_PASSWORD,R_PHONE,R_EMAIL,R_F_NAME,R_L_NAME)\r\n"
				+ "VALUES (?,?,?,?,?,?)";

		try (PreparedStatement stmt = AuthenticationModel.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, registration.getUsername());
			stmt.setString(2, registration.getPassword());
			stmt.setInt(3, Integer.parseInt(registration.getPhone()));
			stmt.setString(4, registration.getEmail());
			stmt.setString(5, registration.getF_Name());
			stmt.setString(6, registration.getL_Name());

			count = stmt.executeUpdate();
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println(e);
			return false;
		}
		if (count == 1)
			return true;
		else
			return false;
	}
}
