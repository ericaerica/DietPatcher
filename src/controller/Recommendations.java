package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import database.DataManager;
import model.UserBean;

public class Recommendations extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		HttpSession session = request.getSession(false);
	    UserBean usr = (session != null) ? (UserBean) session.getAttribute("uBean") : null;
	    if (usr == null) {
	        response.sendRedirect("LoginForm.html"); // No logged-in user found, so redirect to login page.
	    }else{
			// 1. get received JSON data from request
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if(br != null){
				json = br.readLine();
			}
			
			// 2. initiate jackson mapper
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	    	// 3. Convert received JSON to String
	    	String nutrientName = mapper.readValue(json, String.class);
	    	String nutrientID = DataManager.getNutrientIDFromName(nutrientName);
	    	System.out.println(nutrientName + " - "+nutrientID);
	    	// 4. Go get the meal plan table
	    	UserBean user = (UserBean) request.getSession().getAttribute("uBean");
	    	
	    	ArrayList<Object> userRec = DataManager.getBestEatenFood(user, nutrientID);
	    	double userRec_amount = (Double)userRec.get(0);
	    	ArrayList<String> userRec_food = (ArrayList<String>)userRec.get(1);
	    	
	    	ArrayList<Object> peerRec = DataManager.getPeerFood(user, nutrientID);
	    	ArrayList<Double>  peerRec_amount = (ArrayList<Double>)peerRec.get(0);
	    	ArrayList<String> peerRec_food = (ArrayList<String>)peerRec.get(1);
	    	
	    	ArrayList<String> absoluteRec = DataManager.getBestFood(nutrientID);
	    	String absoluteRec_amount = absoluteRec.get(1);
	    	String absoluteRec_food = absoluteRec.get(0);
	    	
	    	//First recommendation
	    	String output = "<br><div id='lower_rec'><div class='panel panel-default simpleRec'><div class='panel-heading'>Based on your habits</div><div class='panel-body'>In the past you have eaten <b>"+DataManager.getFoodNameFromFoodId(userRec_food.get(0))+"</b>, "
	    			+ "you could try it again! It contains <b>"+userRec_amount+DataManager.getMeasureUnit(nutrientID)+" for each 100g</b>!  "
	    			+ "</div><div class='panel-footer'>"
	    			+ "<a href='#adder' class='btn btn-success addToMealplan' onclick=\"addRec('"+DataManager.getFoodNameFromFoodId(userRec_food.get(0)).replace(',',' ')+"')\";'>"
	    			+ "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span> "
	    			+ " Add it!"
	    			+ "</a>"
	    			+ "</div>"
	    			+ "</div>";
	    	//Second recommendation
	    	output+="<div class='panel panel-default simpleRec'><div class='panel-heading'>Based on your tags</div><div class='panel-body'>Other people with your tags have eaten <b>"+DataManager.getFoodNameFromFoodId(peerRec_food.get(0))+"</b>, so you may like it too!"
	    			+ " It contains <b>"+peerRec_amount.get(0)+DataManager.getMeasureUnit(nutrientID)+" for each 100g</b>!  "
	    			+ "</div><div class='panel-footer'>"
	    			+ "<a href='#adder' class='btn btn-success addToMealplan' onclick=\"addRec('"+DataManager.getFoodNameFromFoodId(peerRec_food.get(0)).replace(',',' ')+"')\";'>"
	    			+ "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span> "
	    			+ " Add it!"
	    			+ "</a>"
	    			+ "</div>"
	    			+ "</div>";
	    	//Third recommendation
	    	output+="<div class='panel panel-default simpleRec'><div class='panel-heading'>What we suggest you</div><div class='panel-body'>We personally suggest you to try <b>"+DataManager.getFoodNameFromFoodId(absoluteRec_food)+"</b>, since in our databases it is one of the richest foods in this nutrient!"
	    			+ " It contains <b>"+absoluteRec_amount+DataManager.getMeasureUnit(nutrientID)+" for each 100g</b>!  "
	    			+ "</div><div class='panel-footer'>"
	    			+ "<a href='#adder' class='btn btn-success addToMealplan' onclick=\"addRec('"+DataManager.getFoodNameFromFoodId(absoluteRec_food).replace(',',' ')+"')\";'>"
	    			+ "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span> "
	    			+ " Add it!"
	    			+ "</a>"
	    			+ "</div>"
	    			+ "</div>";
	    	
	    	
	    	output+="<br>Want more recommendations for this nutrient?  "
	    			+ "<a id='goToMoreRec' href='#moreRec' class='btn btn-info btn-xs'>"
	    			+ "<span class=' glyphicon glyphicon-list-alt' aria-hidden='true'></span> "
	    			+ " Click here"
	    			+ "</a>";
	    	output+="  to get a list of foods to add to your diet!</div><br>";
	    	
	    	output+="<div id='moreRec' class='panel panel-primary'><div class='panel-heading'>Food suggestions for "+nutrientName+"</div><br>"
	    			+ "<div class='panel-body'><div class='panel panel-default'>"
	    			+ "<div class='panel-heading'  id='moreRecSubtitle'>Suggestions based on your previously eaten foods, having the highest amount of "+nutrientName+":</div>"
	    			+ "<div class='panel-body'><table class='table'><thead><th>Name</th><th>Amount per 100g</th></thead>";
	    	for (String food : userRec_food){
	    		output+="<tr><td>"+DataManager.getFoodNameFromFoodId(food)+"</td><td>"+userRec_amount+DataManager.getMeasureUnit(nutrientID)+"</td><td>"
	    				+ "<a href='#adder' class='btn btn-success btn-xs addToMealplan' onclick=\"addRec('"+DataManager.getFoodNameFromFoodId(food).replace(',',' ')+"')\";'>"
		    			+ "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span> "
		    			+ " Add it!"
		    			+ "</a>"
	    				+ "</td></tr>";
	    	}
	    	output+="</table></div></div>";
	    	
	    	output+="<div class='panel panel-default'>"
	    			+ "<div class='panel-heading' id='moreRecSubtitle'>Suggestions based on the food that other users having your same tags have eaten, having the highest amount of "+nutrientName+":</div>"
	    			+ "<div class='panel-body'><table class='table'><thead><th>Name</th><th>Amount per 100g</th></thead>";
	    	for (String food : peerRec_food){
	    		output+="<tr><td>"+DataManager.getFoodNameFromFoodId(food)+"</td><td>"+peerRec_amount.get(0)+DataManager.getMeasureUnit(nutrientID)+"</td><td>"
	    				+ "<a href='#adder' class='btn btn-success btn-xs addToMealplan' onclick=\"addRec('"+DataManager.getFoodNameFromFoodId(food).replace(',',' ')+"')\";'>"
		    			+ "<span class='glyphicon glyphicon-plus' aria-hidden='true'></span> "
		    			+ " Add it!"
		    			+ "</a>"
	    				+ "</td></tr>";
	    	}
	    	output+="</table></div></div></div>";

	    	output	+= "<a id='cancelMoreRec' class='btn btn-default' href='#statistics' role='button'>Cancel</a></div></div>";
			// 5. Set response type to JSON
			response.setContentType("application/json");		    

			// 6. Send the string as JSON to client
	    	mapper.writeValue(response.getOutputStream(), output);
	    }
		
		
    }
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException{
		
			
	}
}

