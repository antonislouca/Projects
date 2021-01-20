package epl342Project;

import java.sql.*;
import java.sql.Date;
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
			AdminFunctions.conn = conn;
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

	public static User authenticate(String username, String password) {
		List<Object[]> results = new ArrayList<Object[]>();


		String query = String.format(
				"SELECT  UR.uid\r\n"
				+ "FROM    GP_user UR\r\n"
				+ "WHERE UR.username = '"+ username +"' "
				+ "AND UR.password = '"+ password +"' ");
		results = ProfileModel.getDataFromDB(query)	;
		
		if (results == null || results.isEmpty()) return null;

		User user = new User((int)results.get(0)[0]);

		
		return user;
	}

	public static boolean register(Registration registration) {
		// columns
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {cstmt = AuthenticationModel.conn.prepareCall(
				"{call insertUser(?,?,?,?,?,?)}",				
				ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				cstmt.setString("uname", registration.getUsername());
				cstmt.setString("fname", registration.getF_Name());
				cstmt.setString("lname", registration.getL_Name());
				cstmt.setString("pass", registration.getPassword());
				cstmt.setString("em", registration.getEmail());
				cstmt.setDate("bday", Date.valueOf(registration.getBirthday()));
				
				cstmt.execute();
		
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
	
}
