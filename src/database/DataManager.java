package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import utils.HibernateUtils;
import model.UserBean;

public class DataManager {
   

	public static boolean saveUser(UserBean user){
		//Standard transaction begin
		SessionFactory sf = HibernateUtils.getSessionFactory();
		Session hibernateSession = sf.openSession();
		hibernateSession.beginTransaction();
		
		//Saving/updating user
		hibernateSession.saveOrUpdate(user);

		return true;
	}

	public static UserBean getUser(){
		//Standard transaction begin
		SessionFactory sf = HibernateUtils.getSessionFactory();
		Session hibernateSession = sf.openSession();
		hibernateSession.beginTransaction();
		
		//TODO
		
		return null;
	}
	public static boolean existsUser(UserBean user){
		//Standard transaction begin
		SessionFactory sf = HibernateUtils.getSessionFactory();
		Session hibernateSession = sf.openSession();
		hibernateSession.beginTransaction();
		
		//Getting all users from the table
		List<UserBean> users = hibernateSession.createQuery("from userbean").list();
		return true;
	}
	
	
	
	
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
