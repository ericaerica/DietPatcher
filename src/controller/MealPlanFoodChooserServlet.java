package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.fasterxml.jackson.databind.ObjectMapper;

import database.DataManager;


public class MealPlanFoodChooserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException{
		
			// 1. get received JSON data from request
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String json = "";
			if(br != null){
				json = br.readLine();
			}
			// 2. initiate jackson mapper
	    	ObjectMapper mapper = new ObjectMapper();
	    	
	    	// 3. Convert received JSON to String
	    	String substring = mapper.readValue(json, String.class);

	    	// 4. Get list of suggestions
	    	ArrayList<String[]> foodz = (ArrayList<String[]>)DataManager.getFood(substring);
	    	ArrayList<String[]> suggestions = new ArrayList<String[]>();
	    	int i = 0;
	    	if(foodz.size()>60){
	    		for (String[] s : foodz){
		    		if (i<30){suggestions.add(s);}
		    		i++;
		    	}
	    	} else {
	    		for(String[] s : foodz){
	    			suggestions.add(s);
	    		}
	    	}
	    	
			// 5. Set response type to JSON
			response.setContentType("application/json");		    

			// 6. Send the string as JSON to client
	    	mapper.writeValue(response.getOutputStream(), suggestions);
		}
	}


