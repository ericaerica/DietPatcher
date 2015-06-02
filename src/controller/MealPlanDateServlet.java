package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import model.UserBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import database.DataManager;


public class MealPlanDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException{
			System.out.println("I'M IN!");
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
	    	String s = "";
	    	String[] foodId = output.get(0);
	    	String[] foodAmount = output.get(1);
	    	for (int i = 0; i < foodId.length; i++) {
				s +="<tr><td>"+foodId[i]+"</td><td>"+foodAmount[i]+"</td></tr>";
			}
			// 5. Set response type to JSON
			response.setContentType("application/json");		    

			// 6. Send ArrayList<String> as JSON to client
	    	mapper.writeValue(response.getOutputStream(), s);
		}
	}


