package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.UserBean;

public class DataManager {
	//TODO riordinare i metodi e controllare che per ogni save ci sia un get e viceversa
	private static Connection connection = null;
	
	private static void setConnection(Connection con) {
		connection = con;
	}
	
	/**
	 * This method connects the system with the DB.
	 * 
	 * @return If it was able to connect, returns true, otherwise false.
	 */
	public static boolean connect() {
		boolean result = false;
		try {// Try driver
			Class.forName("org.postgresql.Driver");
			try {// Try connection
				setConnection(DriverManager.getConnection(
						"jdbc:postgresql://alcor.inf.unibz.it:5432/RSC",
						"etomaselli", "uniDradcliffe1!"));
				result = true;
			} catch (SQLException e) {
				System.err.println("Connection Failed! Check output console");
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
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
	public static boolean disconnect() {
		boolean result = false;

		try {
			connection.close();
			result = true;
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
	public static boolean userExists(UserBean user) {
		boolean opResult = false;

		Statement st1 = null;
		ResultSet rs = null;

		if (connection != null) {
			try {
				st1 = connection.createStatement();
				rs = st1.executeQuery("SELECT * FROM userbean WHERE userbean.email = "
						+ "'" + user.getEmail() + "'" + ";");
				if (rs.next()) {
					opResult = true;
					//System.out.println(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return opResult;
	}
	
	//TODO javadoc
	public static boolean saveUser(UserBean user) {
		boolean opResult = false;
		if (connection != null) {
			try {
				if (userExists(user)) {
					Statement st2 = connection.createStatement();
					st2.executeUpdate("UPDATE userbean SET " + "email=" + "'"
							+ user.getEmail() + "'" + "," + "username=" + "'"
							+ user.getUsername() + "'" + "," + "password="
							+ "'" + user.getPassword() + "'" + "," + "gender="
							+ "'" + user.getGender() + "'" + "," + "age="
							+ user.getAge() + "," + "height="
							+ user.getHeight() + "," + "weight="
							+ user.getWeight() + "," + "waist="
							+ user.getWaist() + "WHERE userbean.email=" + "'"
							+ user.getEmail() + "'" + ";");
					opResult = true;
					st2.close();
				} else {
					System.out.println("INSERTING");
					Statement st3 = connection.createStatement();
					st3.executeUpdate("INSERT INTO userbean VALUES (" + "'"
							+ user.getEmail() + "'" + "," + "'"
							+ user.getUsername() + "'" + "," + "'"
							+ user.getPassword() + "'" + "," + "'"
							+ user.getGender() + "'" + "," + user.getAge()
							+ "," + user.getHeight() + "," + user.getWeight()
							+ "," + user.getWaist() + ");");
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

	//TODO javadoc
	public static UserBean getUser(String username, String password) {
		UserBean user = null;
		Statement st = null;
		ResultSet rs = null;

		if (connection != null) {
			try {
				st = connection.createStatement();
				String query = "SELECT * " + "FROM userbean "
						+ "WHERE userbean.username=" + "'" + username + "'"
						+ " AND userbean.password = " + "'" + password + "'"
						+ ';';
				rs = st.executeQuery(query);

				if (rs.next()) {
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
	
	/**
	 * This methods makes a query to the database to obtain a list of food descriptions that contain the partial name
	 * inserted by the user.
	 * 
	 * @param partialName	the partial name of the food searched by the user
	 * @return	ArrayList<String>	list of food descriptions
	 */
	public static ArrayList<String> getFood(String partialName){
		ArrayList<String> foodList = new ArrayList<String>();
		Statement st = null;
		ResultSet rs = null;
		
		if (connection != null) {
			try {
				st = connection.createStatement();
				String query = "SELECT long_desc " + "FROM food_des "
						+ "WHERE food_des.long_desc LIKE '%" + partialName + "%';";
				rs = st.executeQuery(query);

				while (rs.next()) {
					foodList.add((rs.getString(3)));
				}
				st.close();

			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}

		}
		
		return foodList;
	}
	
	/**
	 * This method saves or updates the tags chosen by the user in the database.
	 * 
	 * @param newTags	ArrayList of tags to save
	 */
	public static void saveTags(UserBean user, ArrayList<String> newTags){
		if (connection != null) {
			try {
				if (userExists(user)) {
					ArrayList<String> oldTags = getTags(user);
					
					if(oldTags.isEmpty()){		//there are no tags saved for this user
						Statement st = connection.createStatement();
						//save the tags
						st.executeUpdate("INSERT INTO userxtag VALUES (" + "'"	//TODO
								+ user.getEmail() + "'" + "," + "'"
								+ user.getUsername() + "'" + "," + "'"
								+ user.getPassword() + "'" + "," + "'"
								+ user.getGender() + "'" + "," + user.getAge()
								+ "," + user.getHeight() + "," + user.getWeight()
								+ "," + user.getWaist() + ");");
						st.close();
					} else {					//there are already some tags saved for this user
						Statement st = connection.createStatement();
						//update the tags
						st.executeUpdate("UPDATE userbean SET email=" + "'"		//TODO
								+ user.getEmail() + "'" + "WHERE userbean.email=" + "'"
								+ user.getEmail() + "'" + ";");
						st.close();
					}
				}
			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method returns the list of tags of the user.
	 * 
	 * @param user	UserBean
	 * @return	ArrayList<String>
	 */
	public static ArrayList<String> getTags(UserBean user) {
		ArrayList<String> tags = new ArrayList<String>();
		Statement st = null;
		ResultSet rs = null;

		if (connection != null) {
			try {
				st = connection.createStatement();
				String nestedQuery = "SELECT * FROM userxtag WHERE user = (SELECT id FROM userbean WHERE userbean.email = "
						+ "'" + user.getEmail() + "'" + ");";
				rs = st.executeQuery(nestedQuery);
				
				while (rs.next()) {
					tags.add((rs.getString(3)));
				}
			} catch (SQLException e) {
				System.out.println("error in nestedQuery");
				e.printStackTrace();
			}
		}
		return tags;
	}
}
