package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.UserBean;

public class DataManager {
	private static Connection connection = null;
	
	private static void setConnection(Connection con) {
		connection = con;
	}
	
	/**
	 * This method connects the system with the DB.
	 * 
	 * @return If it was able to connect, returns true, otherwise false.
	 */
	public static boolean connect(){
		boolean result = false;
		try {//Try driver
			Class.forName("org.postgresql.Driver");
			try {//Try connection
				setConnection(DriverManager.getConnection(
						"jdbc:postgresql://alcor.inf.unibz.it:5432/RSC", "etomaselli",
						"uniDradcliffe1!"));
				result = true;
			} catch (SQLException e) {
				System.err.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e){
			System.err.println("MISSING PostgreSQL JDBC Driver");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * This method closes the connection with the DB.
	 * 
	 * @return If it succeeds returns true, otherwise false.
	 */
	public static boolean disconnect(){
		boolean result = false;
		
		try {
			connection.close();
			result= true;
		} catch (SQLException e) {
			System.err.println("Couldn't close connection");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 *  
	 * @param user
	 * @return True if the user exists in the DB, false if it doesn't
	 */
	public static boolean userExists(UserBean user){
		boolean opResult = false;
		
		Statement st1 = null;
        ResultSet rs = null;
        String output = "";
        
		if (connection != null) {
			try {
				st1 = connection.createStatement();
				rs = st1.executeQuery("SELECT * FROM userbean WHERE userbean.email = "+"'"+user.getEmail()+"'"+";");
				if (rs.next()) {
					opResult = true;
					System.out.println(rs.getString(1));
				} 
			} catch (SQLException e) {
				e.printStackTrace();
			}
	
		}
		return opResult;
	}
	
	public static boolean saveUser(UserBean user){
		boolean opResult = false;
		if (connection != null) {
			try {
				if (userExists(user)) {
					System.out.println("UPDATING");
					Statement st2 = connection.createStatement();
					st2.executeUpdate("UPDATE userbean SET "
							+ "userbean.username="+"'"+user.getUsername()+"'"+ ","
							+ "userbean.password="+"'"+user.getPassword()+"'"+ ","
							+ "userbean.gender="+"'"+user.getGender()+"'"+ ","
							+ "userbean.age="+user.getAge()+ ","
							+ "userbean.height="+user.getHeight()+ ","
							+ "userbean.weight="+user.getWeight()+ ","
							+ "userbean.waist="+user.getWaist()
							+ "WHERE userbean.email="+"'"+user.getEmail()+"'"+";");
					opResult = true;
					st2.close();
	            }else{
	            	System.out.println("INSERTING");
	            	Statement st3 = connection.createStatement();
	            	st3.executeUpdate("INSERT INTO userbean VALUES ("
	            			+"'"+user.getEmail()+"'"+ ","
	            			+"'"+user.getUsername()+"'"+ ","
							+"'"+user.getPassword()+"'"+ ","
							+"'"+user.getGender()+"'"+ ","
							+user.getAge()+ ","
							+user.getHeight()+ ","
							+user.getWeight()+ ","
							+user.getWaist()
	            			+ ");");
	            	System.out.println("DONE!");
	            	opResult = true;
	            	st3.close();
	            }
			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}
	
		}
		return opResult;
	}
	
	public static UserBean getUser(String username, String password){
		System.out.println("inside getUser");
		UserBean user = null;
		Statement st = null;
        ResultSet rs = null;

		if (connection != null) {
			try {
				System.out.println("inside try");
				st = connection.createStatement();
				String query = "SELECT * "
						+ "FROM userbean "
						+ "WHERE userbean.username=" +"'"+ username+"'"
						+ " AND userbean.password = "+"'"+ password+"'"+';';
				System.out.println(query);
				rs = st.executeQuery(query);
				
				if (rs.next()){
					user = new UserBean();
					user.setEmail(rs.getString(1));
					user.setUsername(rs.getString(2));
					user.setPassword(rs.getString(3));
					user.setGender(rs.getString(4));
					user.setAge(rs.getInt(5));
					user.setHeight(rs.getDouble(6));
					user.setWeight(rs.getDouble(7));
					user.setWaist(rs.getDouble(8));
				}
			    st.close();
				
			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}
	
		}

		return user;
	}
}
