package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataManager {

	/*
 		//Setting up the DB Connection
		
		Connection connection = null;
		Statement st = null;
        ResultSet rs = null;
        String output = "";
        
		try {//Try driver
			Class.forName("org.postgresql.Driver");
 
		} catch (ClassNotFoundException e){
			
			System.err.println("MISSING PostgreSQL JDBC Driver");
			e.printStackTrace();
			return;
		}
 
		

		try {//Try connection
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/mydb", "postgres",
					"furantsu16!");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null) {//If you made it
			
			try {
				
				st = connection.createStatement();
				rs = st.executeQuery("SELECT * FROM cities");
				 System.out.println("I'M HEddddEEEEEEEEE");
				if (rs.next()) {
	                output+=rs.getString(1)+"<\br>";
	            }
				
			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}
            
			
		} else {
			System.err.println("Failed to make connection!");
		}
		
	 */
}