package model;

import java.sql.Statement;

public class RecommenderUtils {
	/**
	 * This recommender method finds the food containing the greatest amount of the given nutrient among the list of
	 * foods eaten by the user in the past.
	 * 
	 * @param nutrient
	 * @param user
	 * @return
	 */
	public static void getBestEatenFood(UserBean user, String nutrient){
		
	}
	
	/**
	 * This recommender method finds the foods containing the greatest amount of the given nutrient eaten by other users
	 * with the same tags as the user.
	 * 
	 * @param user
	 * @param nutrient
	 */
	public static void getPeerFood(UserBean user, String nutrient){
		
	}
	
	/**
	 * This recommender method finds the food containing the greatest amount of the given nutrient among the whole
	 * list of foods in the database.
	 * 
	 * @param nutrient
	 * @param user
	 * @return
	 */
	public static void getBestFood(UserBean user, String nutrient){
		
	}
}
/*
 * § per ogni cibo mangiato dall'utente, vedere quello che ne contiene di più [cibo,nutriente]
 * § per ogni utente con gli stessi tag, per ogni cibo mangiato da quel utente,
 * vedere quello che ne contiene di più[tag,utente,cibo,nutriente]
 * § guardare nella tabella dei "cibi in assoluto più nutrienti" il corrispondente
 */