package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import database.DataManager;

public class MealPlanRSAndStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException{
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
			System.out.println(json);
			
			// 2. initiate jackson mapper
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	    	// 3. Convert received JSON to String
	    	String date = mapper.readValue(json, String.class);

	    	// 4. Go get the meal plan table
	    	UserBean user = (UserBean) request.getSession().getAttribute("uBean");
	    	ArrayList<String[]> output = DataManager.getMealPlanFromDate(user, date);
	    	String recommender = "";
	    	String statistics = "";
	    	try{
		    	String[] foodId = output.get(0);
		    	String[] foodAmount = output.get(1);
		    	
		    	//List of nutrients for recommendations
		    	ArrayList<String> rec_desc = new ArrayList<String>();
		    	ArrayList<String> rec_id = new ArrayList<String>();		    	
		    	
		    	//Nutritional values of the current meal plan
		    	ArrayList<String> mealplan_nutr = DataManager.getMealPlanNutrients(user.getId()+"", date);
		    	
		    	//Descriptions, IDs, min-max values for male and female
		    	ArrayList<ArrayList<String>> DescMinMax = DataManager.getDescMinMaxNutrients();
		    	ArrayList<String> nutr_desc = DescMinMax.get(0);
		    	ArrayList<String> nutr_min_M= DescMinMax.get(1);
		    	ArrayList<String> nutr_min_F= DescMinMax.get(2);
		    	ArrayList<String> nutr_max = DescMinMax.get(3);
		    	ArrayList<String> nutr_id = DescMinMax.get(4);
		    	
		    	//For every single nutrient we create a customized bar in html, and we save in an array 
		    	//those nutrients that are lacking.
		    	for(int i = 0; i < mealplan_nutr.size(); i++){
		    		if(!(nutr_desc.get(i).contains(":"))){
			    		statistics+="<div class='nutrientStat'>";
			    		statistics+=nutr_desc.get(i);
			    		statistics+="<div class='progress'><div class='progress-bar ";
			    		if (user.getGender().equals("Male")){
			    			//if male
			    			if(Double.parseDouble(mealplan_nutr.get(i))>Double.parseDouble(nutr_max.get(i)) && !nutr_max.get(i).equals("0") ){
			    				//if the value is higher than max - Danger
			    				statistics+="progress-bar-danger";
			    			} else if(Double.parseDouble(mealplan_nutr.get(i))<Double.parseDouble(nutr_min_M.get(i))){
			    				//if the value is lower than min - Warning
			    				statistics+="progress-bar-warning";
			    				rec_desc.add(nutr_desc.get(i));
			    				rec_id.add(nutr_id.get(i));
			    			} else {
			    				statistics+="progress-bar-success";
			    			}
			    		} else {
			    			//if female
			    			if(Double.parseDouble(mealplan_nutr.get(i))>Double.parseDouble(nutr_max.get(i)) && !nutr_max.get(i).equals("0") ){
			    				//if the value is higher than max - Danger
			    				statistics+="progress-bar-danger";
			    			} else if(Double.parseDouble(mealplan_nutr.get(i))<Double.parseDouble(nutr_min_F.get(i))){
			    				//if the value is lower than min - Warning
			    				statistics+="progress-bar-warning";
			    				rec_desc.add(nutr_desc.get(i));
			    				rec_id.add(nutr_id.get(i));
			    			} else {
			    				statistics+="progress-bar-success";
			    			}
			    		}
						statistics+=" progress-bar-striped active' role='progressbar' ";
						statistics+="aria-valuenow='"+mealplan_nutr.get(i);
						if(user.getGender().equals("Male")){
							double percentage = Double.parseDouble(mealplan_nutr.get(i))/Double.parseDouble(nutr_min_M.get(i))*100;
							if(nutr_min_M.get(i).equals("0") || mealplan_nutr.get(i).equals("0") || percentage>100){
								percentage=100;
							}
							
							statistics+="' aria-valuemin='0' aria-valuemax='"+round(Double.parseDouble(nutr_min_M.get(i)),2);
							statistics+="' style='width:"+percentage+"%'>"+round(Double.parseDouble(mealplan_nutr.get(i)),2)+"/"+round(Double.parseDouble(nutr_min_M.get(i)),2)+"</div></div></div>";
						} else {
							double percentage = Double.parseDouble(mealplan_nutr.get(i))/Double.parseDouble(nutr_min_F.get(i))*100;
							if(nutr_min_M.get(i).equals("0") || mealplan_nutr.get(i).equals("0") || percentage>100){
								percentage=100;
							}
							statistics+="' aria-valuemin='0' aria-valuemax='"+round(Double.parseDouble(nutr_min_F.get(i)),2);
							statistics+="' style='width:"+percentage+"%'>"+round(Double.parseDouble(mealplan_nutr.get(i)),2)+"/"+round(Double.parseDouble(nutr_min_F.get(i)),2)+"</div></div></div>";
						}
		    		}
				}//*******************************
		    	
		    	recommender+="<div id='recommendations'>"
		    			+ "<div id='upper_rec'>"
		    			+ "Here are some recommendations to Patch your Diet! <br>We saw that you lack in "
		    			+"<select class='form-control' name='rec_food' id='rec_food' onchange='getSpecificRec();'>";
		    	for(int i = 0; i<rec_desc.size(); i++){
		    		recommender+="<option value'"+rec_id.get(i)+"'>"+rec_desc.get(i)+"</option>";
		    	}
				recommender+="</select>"
		    			+ "</div> <div id='loading'></div>"
		    			+ "<div id='lower_rec_container'></div></div>";
		    	
	    	}catch(Exception e){}
	    	
	    	String final_output = recommender+statistics;
			// 5. Set response type to JSON
			response.setContentType("application/json");		    

			// 6. Send ArrayList<String> as JSON to client
	    	mapper.writeValue(response.getOutputStream(), final_output);
	    }
		}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	}