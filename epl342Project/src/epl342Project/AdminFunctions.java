package epl342Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminFunctions {

	private static String dbConnString2 = "jdbc:sqlserver://mssql.cs.ucy.ac.cy;";

	static Connection conn ;
	private static boolean dbDriverLoaded = false;
	private static FileWriter writer = null;
	static Scanner scan = new Scanner(System.in);
	// private static Statement stmt=null;
	public static void main(String[] args) {

		String userN = "user=";
		String pass = "password=";
		Scanner scan = new Scanner(System.in);
		userN = userN + scan.next() + ";";
		pass = pass + scan.next() + ";";
		dbConnString2 = dbConnString2 + userN + pass;
		AdminFunctions.connectToDB();
		//exportDB();
		//deleteDataFromTables();
	//	ArrayList<String>files=new ArrayList<>();
		importData("import.txt");

	}

	public static void importData(String file) {
	//	try {
			File f= new File(file);
			try {
				scan=new Scanner(f);
				scan.useDelimiter("\\Z"); 
				String input=scan.next();
				System.out.println(input);
				Statement stmt = conn.createStatement();
				stmt.executeQuery(String.format("{call spImportData ('%s')}",input));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
	}

	public static void deleteDataFromTables() {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeQuery("{call spDeleteRows}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void exportDB() {
		try {
			writer = new FileWriter("DataBaseExport.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			writer.write("GP_user:\n");
			executeProcedure("spExportUser");

			writer.write("GP_album:\n");
			executeProcedure("spExportAlbum");

			writer.write("GP_privacy:\n");
			executeProcedure("spExportPrivacy");

			writer.write("GP_photos:\n");
			executeProcedure("spExportPhotos");

			writer.write("GP_videos:\n");
			executeProcedure("spExportVideos");

			writer.write("GP_links:\n");
			executeProcedure("spExportLinks");

			writer.write("GP_events:\n");
			executeProcedure("spExportEvents");

			writer.write("GP_friendswith:\n");
			executeProcedure("spExportFriendsWith");

			writer.write("GP_activities:\n");
			executeProcedure("spExportActivities");

			writer.write("GP_interestedin:\n");
			executeProcedure("spExportInterestedIn");

			writer.write("GP_likes:\n");
			executeProcedure("spExportLikes");

			writer.write("GP_commentsOnAlbums:\n");
			executeProcedure("spExportcommentsOnAlbums");

			writer.write("GP_commentsOnVideos:\n");
			executeProcedure("spExportcommentsOnVideos");

			writer.write("GP_location:\n");
			executeProcedure("spExportLocation");

			writer.write("GP_participates_in:\n");
			executeProcedure("spExportParticipates");

			writer.write("GP_friendRequest:\n");
			executeProcedure("spExportFriendRequests");

			writer.write("GP_works:\n");
			executeProcedure("spExportworks");

			writer.write("GP_worksFor:\n");
			executeProcedure("spExportWorksFor");

			writer.write("GP_education:\n");
			executeProcedure("spExportEducation");

			writer.write("GP_educatedIn:\n");
			executeProcedure("spExportEducatedIn");

			writer.write("GP_quotes:\n");
			executeProcedure("spExportQuotes");

			writer.write("GP_quotedOn:\n");
			executeProcedure("spExportQuotedOn");

			writer.write("GP_updates:\n");
			executeProcedure("spExportUpdates");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		AdminFunctions.disconnectToDB();
		// System.out.println(dbConnString2);

		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getUserinfo() {

		try {
			writer.write("GP_user:\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		executeProcedure("spExportUser");
	}

	public static void executeProcedure(String procedure) {
		ArrayList<Object[]> result = new ArrayList<Object[]>();

		try {
//		      CallableStatement stmt = conn.prepareCall("{call dbo.spExportUser}");
//
//		      boolean results = stmt.execute();
//		      int rsCount = 0;

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("{call " + procedure + "}");

			int cols;
			try {
				cols = rs.getMetaData().getColumnCount();
				for (int i = 0; i < cols; i++)
					writer.write(rs.getMetaData().getColumnName(i + 1) + "\t");
				writer.write("\n");
				while (rs.next()) {
					Object[] attributes = new Object[cols];
					for (int i = 0; i < cols; i++) {
						attributes[i] = rs.getObject(i + 1);

					}

					result.add(attributes); // many attributes make a record

				}
				rs.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			for (int i = 0; i < result.size(); i++) {
				for (int j = 0; j < result.get(i).length; j++)
					writer.write(result.get(i)[j] + "\t");
				writer.write("\n");

			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static ArrayList<Object[]> getDataFromDB(String query) {
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
				conn = DriverManager.getConnection(dbConnString2);
				conn.setAutoCommit(true);
				conn.setHoldability(ResultSet.HOLD_CURSORS_OVER_COMMIT);
			} else if (conn.isClosed()) {
				conn = DriverManager.getConnection(dbConnString2);
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
}
