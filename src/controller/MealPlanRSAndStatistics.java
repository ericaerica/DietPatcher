package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

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
		    	//List of nutrients for recommendations
		    	ArrayList<String> rec_desc = new ArrayList<String>();
		    	ArrayList<String> rec_id = new ArrayList<String>();		    	
		    	
		    	ArrayList<String> nutr_values = DataManager.getMealPlanNutrients(user.getId()+"", date);
		    	
		    	//Descriptions, IDs, min-max values for male and female
		    	ArrayList<ArrayList<String>> DescMinMax = DataManager.getDescMinMaxNutrients();
		    	ArrayList<String> nutr_desc = DescMinMax.get(0);
		    	ArrayList<String> nutr_min_M= DescMinMax.get(1);
		    	ArrayList<String> nutr_min_F= DescMinMax.get(2);
		    	ArrayList<String> nutr_max = DescMinMax.get(3);
		    	ArrayList<String> nutr_id = DescMinMax.get(4);

		    	ArrayList<ArrayList<ArrayList<String>>> AllLists = new ArrayList<ArrayList<ArrayList<String>>>();
		    	ArrayList<ArrayList<String>> General_list = new ArrayList<ArrayList<String>>();
		    	ArrayList<ArrayList<String>> Carb_list = new ArrayList<ArrayList<String>>();
		    	ArrayList<ArrayList<String>> Lipid_list = new ArrayList<ArrayList<String>>();
		    	ArrayList<ArrayList<String>> Protein_list = new ArrayList<ArrayList<String>>();
		    	ArrayList<ArrayList<String>> Vitamin_list = new ArrayList<ArrayList<String>>();
		    	ArrayList<ArrayList<String>> Mineral_list = new ArrayList<ArrayList<String>>();
		    	
		    	
		    	AllLists.add(General_list);
		    	AllLists.add(Carb_list);
		    	AllLists.add(Lipid_list);
		    	AllLists.add(Protein_list);
		    	AllLists.add(Vitamin_list);
		    	AllLists.add(Mineral_list);
		    	
		    	for(ArrayList<ArrayList<String>> list : AllLists){
		    		list.add(new ArrayList<String>());
		    		list.add(new ArrayList<String>());
		    		list.add(new ArrayList<String>());
		    		list.add(new ArrayList<String>());
		    		list.add(new ArrayList<String>());
		    		list.add(new ArrayList<String>());
		    	}
		    	List<String> General_IDs = Arrays.asList("203","204","205","206","208");
		    	List<String> Carb_IDs = Arrays.asList("205", "209", "291","269");
		    	List<String> Lipid_IDs = Arrays.asList("204", "645", "646" ,"621","629","851","685","675","606","605","601");
		    	List<String> Protein_IDs = Arrays.asList("203","507","512","503","504","505","506","508","502","501","509","510");
		    	List<String> Vitamin_IDs = Arrays.asList("418","578","404","405","406","410","415","401","324","325","326","328","573","323","430","318","320");
		    	List<String> Mineral_IDs = Arrays.asList("301","312","303","304","315","305","306","317","307","309");
		    	
		    	for(int i = 0; i < nutr_values.size(); i++){
		    		String id = nutr_id.get(i);
		    		System.out.println(id);
		    		if(General_IDs.contains(id)){
		    			System.out.println("a general added");
		    			General_list.get(0).add(nutr_desc.get(i));//Desc
		    			General_list.get(1).add(nutr_min_M.get(i));//MinM
		    			General_list.get(2).add(nutr_min_F.get(i));//MinF
		    			General_list.get(3).add(nutr_max.get(i));//Max
		    			General_list.get(4).add(nutr_id.get(i));//Id
		    			General_list.get(5).add(nutr_values.get(i));//Consumed amount
		    		}
		    		if(Carb_IDs.contains(id)){
		    			System.out.println("a carb added");
		    			Carb_list.get(0).add(nutr_desc.get(i));//Desc
		    			Carb_list.get(1).add(nutr_min_M.get(i));//MinM
		    			Carb_list.get(2).add(nutr_min_F.get(i));//MinF
		    			Carb_list.get(3).add(nutr_max.get(i));//Max
		    			Carb_list.get(4).add(nutr_id.get(i));//Id
		    			Carb_list.get(5).add(nutr_values.get(i));//Consumed amount
		    		}
		    		if(Lipid_IDs.contains(id)){
		    			System.out.println("a lipid added");
		    			Lipid_list.get(0).add(nutr_desc.get(i));//Desc
		    			Lipid_list.get(1).add(nutr_min_M.get(i));//MinM
		    			Lipid_list.get(2).add(nutr_min_F.get(i));//MinF
		    			Lipid_list.get(3).add(nutr_max.get(i));//Max
		    			Lipid_list.get(4).add(nutr_id.get(i));//Id
		    			Lipid_list.get(5).add(nutr_values.get(i));//Consumed amount
		    		}
		    		if(Protein_IDs.contains(id)){
		    			System.out.println("a protein added");
		    			Protein_list.get(0).add(nutr_desc.get(i));//Desc
		    			Protein_list.get(1).add(nutr_min_M.get(i));//MinM
		    			Protein_list.get(2).add(nutr_min_F.get(i));//MinF
		    			Protein_list.get(3).add(nutr_max.get(i));//Max
		    			Protein_list.get(4).add(nutr_id.get(i));//Id
		    			Protein_list.get(5).add(nutr_values.get(i));//Consumed amount
		    		}
		    		if(Vitamin_IDs.contains(id)){
		    			System.out.println("a vitamin added");
		    			Vitamin_list.get(0).add(nutr_desc.get(i));//Desc
		    			Vitamin_list.get(1).add(nutr_min_M.get(i));//MinM
		    			Vitamin_list.get(2).add(nutr_min_F.get(i));//MinF
		    			Vitamin_list.get(3).add(nutr_max.get(i));//Max
		    			Vitamin_list.get(4).add(nutr_id.get(i));//Id
		    			Vitamin_list.get(5).add(nutr_values.get(i));//Consumed amount
		    		}
		    		if(Mineral_IDs.contains(id)){
		    			System.out.println("a mineral added");
		    			Mineral_list.get(0).add(nutr_desc.get(i));//Desc
		    			Mineral_list.get(1).add(nutr_min_M.get(i));//MinM
		    			Mineral_list.get(2).add(nutr_min_F.get(i));//MinF
		    			Mineral_list.get(3).add(nutr_max.get(i));//Max
		    			Mineral_list.get(4).add(nutr_id.get(i));//Id
		    			Mineral_list.get(5).add(nutr_values.get(i));//Consumed amount
		    		}
		    	}
		    	
		    	int k = 0;
		    	String[] titles = {"General","Carbohydrates","Lipids","Proteins","Vitamins","Minerals"};
		    	String[] htmlText = {"","","","","",""};
		    	for(ArrayList<ArrayList<String>> list : AllLists){
		    		htmlText[k]+="<div class='panel panel-default'><div class='panel-heading'>"+titles[k]+"</div>";
		    		htmlText[k]+="<table class='table table-condensed'><col width='50%'><col width='50%'>";
		    		
		    		for(int j = 0; j<list.get(0).size(); j++){
		    			htmlText[k]+="<tr><td>";
		    			htmlText[k]+=list.get(0).get(j);
		    			htmlText[k]+="</td><td><div class='progress-bar ";
			    		if (user.getGender().equals("Male")){
			    			//if male
			    			if(Double.parseDouble(list.get(5).get(j))>Double.parseDouble(list.get(3).get(j)) && !list.get(3).get(j).equals("0") ){
			    				//if the value is higher than max - Danger
			    				htmlText[k]+="progress-bar-danger";
			    			} else if(Double.parseDouble(list.get(5).get(j))<Double.parseDouble(list.get(1).get(j))){
			    				//if the value is lower than min - Warning
			    				htmlText[k]+="progress-bar-warning";
			    				rec_desc.add(list.get(0).get(j));
			    				rec_id.add(list.get(4).get(j));
			    			} else {
			    				htmlText[k]+="progress-bar-success";
			    			}
			    		} else {
			    			//if female
			    			if(Double.parseDouble(list.get(5).get(j))>Double.parseDouble(list.get(3).get(j)) && !list.get(3).get(j).equals("0") ){
			    				//if the value is higher than max - Danger
			    				htmlText[k]+="progress-bar-danger";
			    			} else if(Double.parseDouble(list.get(5).get(j))<Double.parseDouble(list.get(2).get(j))){
			    				//if the value is lower than min - Warning
			    				htmlText[k]+="progress-bar-warning";
			    				rec_desc.add(list.get(0).get(j));
			    				rec_id.add(list.get(4).get(j));
			    			} else {
			    				htmlText[k]+="progress-bar-success";
			    			}
			    		}
			    		htmlText[k]+=" progress-bar-striped active' role='progressbar' ";
			    		htmlText[k]+="aria-valuenow='"+list.get(5).get(j);
						if(user.getGender().equals("Male")){
							double percentage = Double.parseDouble(list.get(5).get(j))/Double.parseDouble(list.get(1).get(j))*100;
							if(nutr_min_M.get(j).equals("0") || list.get(5).get(j).equals("0") || percentage>100){
								percentage=100;
							}
							
							htmlText[k]+="' aria-valuemin='0' aria-valuemax='"+round(Double.parseDouble(list.get(1).get(j)),2);
							htmlText[k]+="' style='width:"+percentage+"%'>"+round(Double.parseDouble(list.get(5).get(j)),2)+"/"+round(Double.parseDouble(list.get(1).get(j)),2)+"</div>";
						} else {
							double percentage = Double.parseDouble(list.get(5).get(j))/Double.parseDouble(list.get(2).get(j))*100;
							if(nutr_min_M.get(j).equals("0") || list.get(5).get(j).equals("0") || percentage>100){
								percentage=100;
							}
							htmlText[k]+="' aria-valuemin='0' aria-valuemax='"+round(Double.parseDouble(list.get(2).get(j)),2);
							htmlText[k]+="' style='width:"+percentage+"%'>"+round(Double.parseDouble(list.get(5).get(j)),2)+"/"+round(Double.parseDouble(list.get(2).get(j)),2)+"</div>";
						}
		    		}
		    		
		    		htmlText[k]+="</table></div>";
		    		k++;
		    	}
		    	
		    	statistics+=htmlText[0]+htmlText[1]+htmlText[2]+htmlText[3]+htmlText[4]+htmlText[5];
		    	
		    	/*
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
		    	*/
		    	recommender+="<div id='recommendations'>"
		    			+ "<div id='upper_rec'>"
		    			+ "Here are some recommendations to Patch your Diet! <br>Please choose a nutrient you lack in: "
		    			+"<select class='form-control' name='rec_food' id='rec_food' onchange='getSpecificRec();'>";
		    	for(int i = 0; i<rec_desc.size(); i++){
		    		recommender+="<option value'"+rec_id.get(i)+"'>"+rec_desc.get(i)+"</option>";
		    	}
				recommender+="</select>"
		    			+ "</div> <div id='loading'></div>"
		    			+ "<div id='lower_rec_container'></div></div>";
		    	
	    	}catch(Exception e){e.printStackTrace();}
	    	recommender = "<div class='panel panel-default'><div class='panel-heading'>Recommendations</div><div class='panel-body'>" + recommender + "</div></div>";
	    	statistics = "<div class='panel panel-default'><div class='panel-heading'>Statistics</div><div class='panel-body'>" + statistics + "</div></div>";
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