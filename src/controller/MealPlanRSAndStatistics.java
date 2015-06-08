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
	    	String outputString = "";
	    	try{
		    	String[] foodId = output.get(0);
		    	String[] foodAmount = output.get(1);
		    	
		    	ArrayList<String> mealplan_nutr = DataManager.getMealPlanNutrients(user.getId()+"", date);
		    	ArrayList<ArrayList<String>> DescMinMax = DataManager.getDescMinMaxNutrients();
		    	ArrayList<String> nutr_desc = DescMinMax.get(0);
		    	ArrayList<String> nutr_min_M= DescMinMax.get(1);
		    	ArrayList<String> nutr_min_F= DescMinMax.get(2);
		    	ArrayList<String> nutr_max = DescMinMax.get(3);
		    	
		    	for(int i = 0; i < mealplan_nutr.size(); i++){
		    		if(!(nutr_desc.get(i).contains(":"))){
		    			System.out.println(nutr_desc.get(i) + " - " + nutr_min_M.get(i) + " - " + nutr_max.get(i));
			    		outputString+="<div class='nutrientStat'>";
			    		outputString+=nutr_desc.get(i);
			    		outputString+="<div class='progress'><div class='progress-bar ";
			    		if (user.getGender()=="m"||user.getGender()=="M"){
			    			//if male
			    			if(Double.parseDouble(mealplan_nutr.get(i))>Double.parseDouble(nutr_max.get(i)) && nutr_max.get(i)!="0" ){
			    				//if the value is higher than max - Danger
			    				outputString+="progress-bar-danger";
			    			} else if(Double.parseDouble(mealplan_nutr.get(i))<Double.parseDouble(nutr_min_M.get(i))){
			    				//if the value is lower than min - Warning
			    				outputString+="progress-bar-warning";
			    			} else {
			    				outputString+="progress-bar-success";
			    			}
			    		} else {
			    			//if female
			    			if(Double.parseDouble(mealplan_nutr.get(i))>Double.parseDouble(nutr_max.get(i)) && nutr_max.get(i)!="0" ){
			    				//if the value is higher than max - Danger
			    				outputString+="progress-bar-danger";
			    			} else if(Double.parseDouble(mealplan_nutr.get(i))<Double.parseDouble(nutr_min_F.get(i))){
			    				//if the value is lower than min - Warning
			    				outputString+="progress-bar-warning";
			    			} else {
			    				outputString+="progress-bar-success";
			    			}
			    		}
						outputString+=" progress-bar-striped active' role='progressbar' ";
						outputString+="aria-valuenow='"+mealplan_nutr.get(i);
						if(user.getGender()=="m"||user.getGender()=="M"){
							double percentage = Double.parseDouble(mealplan_nutr.get(i))/Double.parseDouble(nutr_min_M.get(i))*100;
							if(nutr_min_M.get(i)=="0" || mealplan_nutr.get(i)=="0" || percentage>100){
								percentage=100;
							}
							
							outputString+="' aria-valuemin='0' aria-valuemax='"+nutr_min_M.get(i);
							outputString+="' style='width:"+percentage+"%'>"+mealplan_nutr.get(i)+"/"+round(Double.parseDouble(nutr_min_M.get(i)),2)+"</div></div></div>";
						} else {
							double percentage = Double.parseDouble(mealplan_nutr.get(i))/Double.parseDouble(nutr_min_F.get(i))*100;
							if(nutr_min_M.get(i)=="0" || mealplan_nutr.get(i)=="0" || percentage>100){
								percentage=100;
							}
							outputString+="' aria-valuemin='0' aria-valuemax='"+nutr_min_F.get(i);
							outputString+="' style='width:"+percentage+"%'>"+mealplan_nutr.get(i)+"/"+round(Double.parseDouble(nutr_min_F.get(i)),2)+"</div></div></div>";
						}
		    		}
				}
		    	System.out.println(outputString);
	    	}catch(Exception e){}
	    	
			// 5. Set response type to JSON
			response.setContentType("application/json");		    

			// 6. Send ArrayList<String> as JSON to client
	    	mapper.writeValue(response.getOutputStream(), outputString);
	    }
		}
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	}