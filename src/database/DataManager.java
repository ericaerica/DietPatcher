package database;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Random;

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
				Statement st = connection.createStatement();
				if (userExists(user)) {
					st.executeUpdate("UPDATE userbean SET " + "email=" + "'"
							+ user.getEmail() + "'" + "," + "username=" + "'"
							+ user.getUsername() + "'" + "," + "password="
							+ "'" + user.getPassword() + "'" + "," + "gender="
							+ "'" + user.getGender() + "'" + "," + "age="
							+ user.getAge() + "," + "height="
							+ user.getHeight() + "," + "weight="
							+ user.getWeight() + "," + "waist="
							+ user.getWaist() + "WHERE userbean.email=" + "'"
							+ user.getEmail() + "'" + ";");
				} else {
					System.out.println("INSERTING");
					st.executeUpdate("INSERT INTO userbean VALUES (" + "'"
							+ user.getEmail() + "'" + "," + "'"
							+ user.getUsername() + "'" + "," + "'"
							+ user.getPassword() + "'" + "," + "'"
							+ user.getGender() + "'" + "," + user.getAge()
							+ "," + user.getHeight() + "," + user.getWeight()
							+ "," + user.getWaist() + ");");
				}
				//retrieve user ID and tags and set them for the bean
				ResultSet rs = st.executeQuery("SELECT id FROM userbean WHERE userbean.email=" 
						+ "'" + user.getEmail() + "'" + ";");
				if(rs.next())
					user.setId(rs.getInt(1));
				opResult = true;
				st.close();
				user.setTags(getTags(user));
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
					user.setId(rs.getInt(9));
					user.setTags(getTags(user));
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
	public static ArrayList<String[]> getFood(String partialName){
		ArrayList<String[]> foodList = new ArrayList<String[]>();
		Statement st = null;
		ResultSet rs = null;
		
		String[] words = partialName.split("\\s+");
		
		if (connection != null) {
			try {
				st = connection.createStatement();
				String query = "SELECT ndb_no, long_desc " + "FROM food_des ";
				int i = 0;
				for(String s : words){
					if (i==0)query+="WHERE ";
					if (i>=1)query+="AND ";
					query+="UPPER(food_des.long_desc) LIKE UPPER('%" + s + "%') ";
					i++;
				}
				query+=";";
				rs = st.executeQuery(query);
				while (rs.next()) {
					String[] couple = new String[2];
					couple[0]=rs.getString(1);
					couple[1]=rs.getString(2);
					foodList.add(couple);
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
			/*try {
				if (userExists(user)) {
					ArrayList<String> oldTags = getTags(user);
					if(oldTags.isEmpty()){		//there are no tags saved for this user
						Statement st = connection.createStatement();
						//save the tags
						for(String tag : newTags){
							String query1 = "SELECT tag.id FROM tag WHERE tag.name='" + tag + "';";
							System.out.println(query1);
							ResultSet rs = st.executeQuery(query1);
							int tagId = 0;
							if(rs.next()){
								tagId = rs.getInt(1);
								String query2 = "INSERT INTO userxtag VALUES (" + tagId + "," + user.getId() + ");";
								System.out.println(query2);
								st.executeUpdate(query2);
							}							
							
						}
						user.setTags(newTags);	//set the tags of the userbean with the new list
						st.close();
					} else {					//there are already some tags saved for this user
						ArrayList<String> tagsToAdd = new ArrayList<String>();
						for(String neu : newTags){
							if(!oldTags.contains(neu)){
								tagsToAdd.add(neu);
							}
						}
						*/
			try {
						Statement st1 = connection.createStatement();						
						String query2 = "DELETE FROM userxtag WHERE userxtag.user='"+user.getId()+"';";							
						st1.executeUpdate(query2);
						st1.close();
						Statement st = connection.createStatement();
						for(String tag : newTags){
							String query1 = "SELECT tag.id FROM tag WHERE tag.name=" + "'" + tag + "'" + ";";
							//System.out.println(query1);
							ResultSet rs = st.executeQuery(query1);
							int tagId = 0;
							while(rs.next()){
								tagId = rs.getInt(1);
							}							
							String query3 = "INSERT INTO userxtag VALUES (" + tagId + "," + user.getId() + ");";
							st.executeUpdate(query3);
						}
						user.setTags(newTags);	//set the tags of the userbean with the new list
						st.close();
			//		}
			//	}
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
		System.out.println("inside getTags");
		ArrayList<String> tags = new ArrayList<String>();
		Statement st = null;
		ResultSet rs = null;

		if (connection != null) {
			try {
				st = connection.createStatement();
				String joinQuery = "SELECT userxtag.tag, tag.name, userxtag.user FROM tag INNER JOIN userxtag "
						+ "ON tag.id = userxtag.tag WHERE userxtag.user=" + user.getId() + ";";
				rs = st.executeQuery(joinQuery);
				while (rs.next()) {
					tags.add((rs.getString(2)));
				}
			} catch (SQLException e) {
				System.out.println("error in joinQuery");
				e.printStackTrace();
			}
		}
		return tags;
	}
	
	/**
	 * TODO
	 * 
	 * @param foods
	 * @return
	 */
	public static ArrayList<String> getFoodNameFromFoodId(String[] foods){
		connect();
		ArrayList<String> foodsId = new ArrayList<String>();
		for (String food : foods) {
			Statement st = null;
			ResultSet rs = null;

			if (connection != null) {
				try {
					st = connection.createStatement();
					String joinQuery = "SELECT food_des.long_desc FROM food_des "
							+ "WHERE food_des.ndb_no='" + food + "';";
					System.out.println(joinQuery);
					rs = st.executeQuery(joinQuery);
					while (rs.next()) {
						foodsId.add(rs.getString(1));
					}
				} catch (SQLException e) {
					System.out.println("error in joinQuery");
					e.printStackTrace();
				}
			}
		}
		return foodsId;
	}
	
	/**
	 * TODO
	 * 
	 * @param user
	 * @param date
	 * @param food
	 * @param amount
	 * @return
	 */
	public static boolean saveMealPlan(UserBean user, String date, String[] food, String[] amount) {
		boolean opResult = false;
		if (connection != null) {
			try {
				String foodToAdd = "'{";		//begin to construct the array of food for the database
				String amountToAdd = "'{";		//begin to construct the array of food amounts for the database
				int i = 0;
				for (String f : food) {
					if (i != 0)
						foodToAdd += ",";		//if it is not the first food, add ","
					foodToAdd += '"' + f + '"';	//add the food to the array under construction
					i++;
				}
				foodToAdd += "}'";				//close the array
				
				int j = 0;
				for (String a : amount) {
					if (j != 0)
						amountToAdd += ",";
					amountToAdd += '"' + a + '"';
					j++;
				}
				amountToAdd += "}'";
				
				String nutrToAdd = calculateNutrients(food, amount);
				System.out.println(nutrToAdd);
				if (mealPlanExists(user.getId(), date)) {
					Statement st = connection.createStatement();
					String query = "UPDATE mealplan SET foodlist=" + foodToAdd
							+ "," + "amountlist=" + amountToAdd
							+ "," + "nutrvaluelist=" + nutrToAdd
							+ " WHERE mealplan.user=" + user.getId()
							+ " AND mealplan.date='" + date + "';";
					System.out.println(query);
					st.executeUpdate(query);
					opResult = true;
					st.close();
				} else {
					Statement st = connection.createStatement();

					String query = "INSERT INTO mealplan VALUES ("
							+ user.getId() + ",'" + date + "'," + foodToAdd
							+ "," + amountToAdd + "," + nutrToAdd + ");";
					System.out.println(query);
					st.executeUpdate(query);
					opResult = true;
					st.close();
				}

				userCrono(user.getId(), food);
			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}

		}
		return opResult;
	}
	
	/**
	 * TODO
	 * 
	 * @param id
	 * @param date
	 * @return
	 */
	public static boolean mealPlanExists(int id, String date) {
		boolean opResult = false;

		Statement st1 = null;
		ResultSet rs = null;

		if (connection != null) {
			try {
				st1 = connection.createStatement();
				rs = st1.executeQuery("SELECT * FROM mealplan WHERE mealplan.user = "+ id + "AND mealplan.date='"+date+"';");
				if (rs.next()) {
					opResult = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return opResult;
	}
	
	/**
	 * TODO
	 * 
	 * @param id
	 * @param foods
	 */
	public static void userCrono(int id, String[] foods){
		Statement st2 = null;
		ResultSet rs2 = null;

		if (connection != null) {
			try {
					st2 = connection.createStatement();
					rs2 = st2.executeQuery("SELECT foodlist FROM userxfoodcronology WHERE userxfoodcronology.user = "+id+";");
					if (rs2.next()) {
						
						String[] temp = (String[])(rs2.getArray("foodlist")).getArray();
						ArrayList<String> oldFood = new ArrayList<String>();
						for(String s : temp){
							oldFood.add(s);
						}
					    for (String f : foods){
					    	if(!oldFood.contains(f)){
					    		oldFood.add(f);
					    	}
					    }
					    
					    String foodToAdd = "'{";
						int i = 0;
						for(String f:oldFood){
							if (i!=0)foodToAdd+=",";
							foodToAdd+='"'+f+'"';
							i++;
						}foodToAdd+="}'";
					    
					    Statement st = connection.createStatement();
						String query = "UPDATE userxfoodcronology SET foodlist="+foodToAdd+" WHERE userxfoodcronology.user="+id+";";
						System.out.println(query);
						st.executeUpdate(query);
						st.close();
					} else {
						String foodToAdd = "'{";
						int i = 0;
						for(String f:foods){
							if (i!=0)foodToAdd+=",";
							foodToAdd+='"'+f+'"';
							i++;
						}foodToAdd+="}'";
						Statement st = connection.createStatement();
						String query = "INSERT INTO userxfoodcronology VALUES("+id+","+foodToAdd+")";
						System.out.println(query);
						st.executeUpdate(query);

						st.close();
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method provides a list of all the food ever eaten by the user while using the application.
	 * @param user
	 * @return ArrayList<String>	list of food eaten by the user
	 */
	public static ArrayList<String> getUserCrono(UserBean user){
		Statement st = null;
		ResultSet rs = null;
		ArrayList<String> cronology = new ArrayList<String>();
		
		if(connection != null){
			try {
				st = connection.createStatement();
				rs = st.executeQuery("SELECT foodlist FROM userxfoodcronology WHERE userxfoodcronology.user = "+ user.getId() +";");
				
				if(rs.next()){
					String[] temp = (String[])(rs.getArray("foodlist")).getArray();
					for(String s : temp){
						cronology.add(s);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cronology;
	}
	
	/**
	 * TODO
	 * 
	 * @param user
	 * @param date
	 * @return
	 */
	public static ArrayList<String[]> getMealPlanFromDate(UserBean user, String date){
		connect();
		String[] foodId = new String[200];
		String[] foodAmount = new String[200];
		ArrayList<String[]> output = new ArrayList<String[]>();
			Statement st = null;
			ResultSet rs = null;

			if (connection != null) {
				try {
					st = connection.createStatement();
					String query = "SELECT foodlist, amountlist FROM mealplan WHERE "+'"'+"user"+'"'+"="+ + user.getId() +" AND "+'"'+"date"+'"'+"='" +date + "';";
					//System.out.println(query);
					rs = st.executeQuery(query);
					
					if (rs.next()) {
						Array y = rs.getArray("foodlist");
						foodId = ((String[])(y.getArray()));
						y.free();
						y = rs.getArray("amountlist");
						//for (int i = 0; i < ((String[])(y).getArray()).length; i++) {
						//	System.out.println(((String[])(y).getArray())[i]);
						//}
						foodAmount = ((String[])(y).getArray());					
					}
					
				} catch (SQLException e) {
					System.err.println("error in query");
					e.printStackTrace();
				}
			}
	
			if(foodId[0]!=null && foodAmount[0]!=null){
				output.add(foodId);
				output.add(foodAmount);
			}
			
			
		return output;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String calculateNutrients(String[] foods, String[] amounts){
		Statement st = null;
		ResultSet rs = null;
		
		//Creation of the array		
		ArrayList<String> mealplan_nutrients = new ArrayList<String>();
		
		if (connection != null) {
			try {
				st = connection.createStatement();
				//System.out.println("init query");
				String query = "SELECT nutr_no FROM nutr_def ORDER BY nutr_no;";
				//System.out.println(query);
				rs = st.executeQuery(query);
				while (rs.next()) {
					mealplan_nutrients.add(rs.getString(1));
				}
				st.close();
			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}
		}

		
		Double[] mealplan_nutramounts = new Double[mealplan_nutrients.size()];
		for(int i = 0; i < mealplan_nutramounts.length; i++){
			mealplan_nutramounts[i]=0.0;
		}
		
		for(int i = 0; i<foods.length; i++){
			//Get nutrients for each food
			Statement st1 = null;
			ResultSet rs1 = null;
	
			if (connection != null) {
				try {
					st1 = connection.createStatement();
					System.out.println("init query");
					String query = "SELECT nutr_no, nutr_val FROM nut_data WHERE ndb_no="+"'"+foods[i]+"'"+" ORDER BY nutr_no;";
					//System.out.println(query);
					rs1 = st1.executeQuery(query);System.out.println();
					//System.out.println("***************");
					while (rs1.next()) {
						//For each nutrient, add it to the hastable
						//System.out.println(Double.parseDouble(amounts[i])/100);
						mealplan_nutramounts[mealplan_nutrients.indexOf(rs1.getString(1))]+=(Double.parseDouble(rs1.getString(2)))*(Double.parseDouble(amounts[i])/100);					
					}
					st1.close();
				} catch (SQLException e) {
					System.err.println("ERROR in the query!");
					e.printStackTrace();
				}
			}
		}
		String nutrToAdd = "'{";
		int i = 0;
		for(Double n : mealplan_nutramounts){
			if (i!=0){nutrToAdd+=",";}
			nutrToAdd+='"'+String.valueOf(n)+'"';
			i++;
		}
		nutrToAdd+="}'";
		return nutrToAdd;
	}
	
	public static ArrayList<ArrayList<String>> getDescMinMaxNutrients(){
		Statement st = null;
		ResultSet rs = null;
		ArrayList<String> nutrdesc = new ArrayList<String>();
		ArrayList<String> DRI_RDA_M = new ArrayList<String>();
		ArrayList<String> DRI_RDA_F = new ArrayList<String>();
		ArrayList<String> UL_max_tolerable = new ArrayList<String>();
		ArrayList<ArrayList<String>> output = new ArrayList<ArrayList<String>>();
		
		if (connection != null) {
			try {
				st = connection.createStatement();
				String query = "SELECT nutrdesc, DRI_RDA_M, DRI_RDA_F, UL_max_tolerable"
						+ " FROM nutr_def ORDER BY nutr_no;";
				System.out.println(query);
				rs = st.executeQuery(query);
				while (rs.next()) {
					nutrdesc.add(rs.getString(1));
					DRI_RDA_M.add(rs.getString(2));
					DRI_RDA_F.add(rs.getString(3));
					UL_max_tolerable.add(rs.getString(4));
				}
				st.close();
			} catch (SQLException e) {
				System.err.println("ERROR in the query!");
				e.printStackTrace();
			}
		}
		output.add(nutrdesc);
		output.add(DRI_RDA_M);
		output.add(DRI_RDA_F);
		output.add(UL_max_tolerable);
		return output;
	}
	
	public static ArrayList<String> getMealPlanNutrients(String id, String date){
		Statement st1 = null;
		ResultSet rs = null;
		ArrayList<String> nutrValues = new ArrayList<String>();
		if (connection != null) {
			try {
				st1 = connection.createStatement();
				rs = st1.executeQuery("SELECT nutrvaluelist FROM mealplan WHERE mealplan.user = "+ id + "AND mealplan.date='"+date+"';");
				if (rs.next()) {
					String[] m = (String[])(rs.getArray("nutrvaluelist").getArray());
					for(String s : m){
						nutrValues.add(s);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return nutrValues;
	}
	
	//**********************************************************************************************************//
	//***************************************** RECOMMENDATION METHODS *****************************************//
	//**********************************************************************************************************//

	/*
	 * § per ogni cibo mangiato dall'utente, vedere quello che ne contiene di più [cibo,nutriente]
	 * § per ogni utente con gli stessi tag, per ogni cibo mangiato da quel utente,
	 * vedere quello che ne contiene di più[tag,utente,cibo,nutriente]
	 */

	/**
	 * This recommender method finds the food containing the greatest amount of the given nutrient among the list of
	 * foods eaten by the user in the past.
	 * 
	 * @param nutrient
	 * @param user
	 * @return
	 */
	public static ArrayList<Object> getBestEatenFood(UserBean user, String nutrient){
		System.out.println("inside getbesteaten: user id=" + user.getId());
		ArrayList<Object> food_NutrAmount = new ArrayList<Object>();
		double max = 0.0;
		ArrayList<String> foodIDs = new ArrayList<String>();
		Statement st = null;
		
		if(connection != null){
			try {
				ArrayList<String> eatenFoods = getUserCrono(user);
				st = connection.createStatement();
				
				double[] amounts = new double[eatenFoods.size()];
				int index = 0;
				for(String s : eatenFoods){
					String query3 = "SELECT nut_data.nutr_val FROM nut_data WHERE nut_data.nutr_no=" + "'" + nutrient + "'"
							+ " AND ndb_no=" + "'" + s + "'" + ";";
					ResultSet rs3 = st.executeQuery(query3);
					while(rs3.next()){
						double amount = rs3.getDouble(1);
						amounts[index] = amount;
					}
					index++;
				}
				
				for(double amt : amounts){
					if(amt > max)
						max = amt;
				}
				
				index = 0;
				for(double amt : amounts){
					if(amt == max)
						foodIDs.add(eatenFoods.get(index));
					index++;
				}
				
				st.close();
			} catch (SQLException e) {
				System.out.println("error in best food query");
				e.printStackTrace();
			}
		}
		
		food_NutrAmount.add(max);
		food_NutrAmount.add(foodIDs);
		return food_NutrAmount;
	}

	/**
	 * This recommender method finds the foods containing the greatest amount of the given nutrient eaten by other users
	 * with the same tags as the user.
	 * 
	 * @param user
	 * @param nutrient
	 */
	public static ArrayList<Object> getPeerFood(UserBean user, String nutrient){
		System.out.println("inside getpeer");
		ArrayList<Object> peerFood = new ArrayList<Object>();
		ArrayList<Double> amounts = new ArrayList<Double>();
		ArrayList<String> foods = new ArrayList<String>();
		Statement st = null;
		if(connection != null){
			try {
				st = connection.createStatement();
				ArrayList<String> tags = user.getTags();
				for(String tag : tags){
					String query1 = "SELECT user FROM userxtag WHERE tag=" + "'" + tag + "'" + ";";
					ResultSet rs1 = st.executeQuery(query1);
					while(rs1.next()){
						int userID = rs1.getInt(1);
						if(userID != user.getId()){
							UserBean otherUser = new UserBean();
							otherUser.setId(userID);
							ArrayList<Object> food_amount = getBestEatenFood(otherUser, nutrient);
							double amount = (Double)food_amount.get(0);
							ArrayList<String> otherFoods = new ArrayList<String>();
							
							for(String food : otherFoods){
								foods.add(food);
								amounts.add(amount);
							}
						}
					}
				}
			} catch (SQLException e) {
				System.out.println("error in same tags query");
				e.printStackTrace();
			}
		}
		for(double d : amounts)
			System.out.print(d + "\t");
		System.out.println();
		for(String s : foods)
			System.out.print(s + "\t");
		System.out.println();
		peerFood.add(amounts);
		peerFood.add(foods);
		return peerFood;
	}
	
	/**
	 * This recommender method finds the food containing the greatest amount of the given nutrient among the whole
	 * list of foods in the database. If there is more than one food with the same amount, one is chosen randomly.
	 * 
	 * @param nutrient
	 * @return ArrayList<String>	containing the food ID of the food found and the amount of nutrient per 100g
	 */
	public static ArrayList<String> getBestFood(String nutrient){	//TODO convertirlo per lista di nutrienti
		ArrayList<String> food_NutrAmount = new ArrayList<String>();
		ArrayList<String> bestFoods = new ArrayList<String>();
		ArrayList<Double> amounts = new ArrayList<Double>();
		String foodId = "";
		String amount = "";
		Statement st = null;
		ResultSet rs = null;
		
		if (connection != null) {
			try {
				st = connection.createStatement();
				String query = "SELECT ndb_no, nutr_val FROM highest_nutr WHERE nutr_no=" + "'" + nutrient + "'" + ";";
				rs = st.executeQuery(query);
				
				while (rs.next()) {
					bestFoods.add(rs.getString(1));
					amounts.add(rs.getDouble(2));
				}
				
				if(bestFoods.size() > 1){						//there is more than one food with the same amount
					Random rnd = new Random();
					int index = rnd.nextInt(bestFoods.size());	//choose randomly one food
					foodId = bestFoods.get(index);
					amount += amounts.get(index);
				} else {
					foodId = bestFoods.get(0);
					amount += amounts.get(0);
				}
				st.close();
			} catch (SQLException e) {
				System.out.println("error in best food query");
				e.printStackTrace();
			}
		}
		food_NutrAmount.add(foodId);
		food_NutrAmount.add(amount);
		System.out.println(food_NutrAmount.get(0) + "\t" + food_NutrAmount.get(1));
		return food_NutrAmount;
	}

	
	public static void database(){
		/*try {
			connect();
			Statement st = connection.createStatement();
			ResultSet rs = null;
			rs = st.executeQuery("SELECT nutr_no FROM nutr_def");
			ArrayList<String> list1 = new ArrayList<String>(); //150 nutrients
			ArrayList<String> list2 = new ArrayList<String>(); //nutrients
			ArrayList<String> list3 = new ArrayList<String>(); //food
			ArrayList<Double> list4 = new ArrayList<Double>(); //values
			
			while(rs.next())
				list1.add(rs.getString(1));
			for(String s : list1){
				String query = "SELECT nutr_no, ndb_no, nutr_val FROM nut_data WHERE nutr_val="
						+ "(SELECT MAX(nutr_val) FROM nut_data WHERE nutr_no=" + "'" + s + "'" + ")"
						+ " AND nutr_no=" + "'" + s + "'" + " ORDER BY nutr_no ASC;";
				ResultSet rs1 = st.executeQuery(query);
				while(rs1.next()){
					list2.add(rs1.getString(1));
					list3.add(rs1.getString(2));
					list4.add(rs1.getDouble(3));
				}
			}
			
			for(int i=0; i<list2.size(); i++){

				if(i<100){
					System.out.println("INSERT INTO highest_nutr (nutr_no, ndb_no, nutr_val) VALUES ("
							+ "'" + list2.get(i) + "'" + ", " + "'" + list3.get(i) + "'" + ", " + list4.get(i) + ");");
				}

				st.executeUpdate("INSERT INTO highest_nutr (nutr_no, ndb_no, nutr_val) VALUES ("
						+ "'" + list2.get(i) + "'" + ", " + "'" + list3.get(i) + "'" + ", " + list4.get(i) + ");");
			}
			
			st.close();
		} catch (SQLException e) {
			System.out.println("ERROR ERROR");
			e.printStackTrace();
		}*/
	}
	
}
