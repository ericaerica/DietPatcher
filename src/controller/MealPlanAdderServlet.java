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


import javax.servlet.http.HttpSession;

import model.ProfileUtils;
import model.UserBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import database.DataManager;


public class MealPlanAdderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String inputDate = request.getParameter("inputDate");
		String[] foodArray = request.getParameterValues("food_name");
		String[] amountArray = request.getParameterValues("food_amount");
		
		
		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("uBean");
		if(inputDate!=null){
			if(foodArray!=null && amountArray!=null && foodArray.length==amountArray.length){
				//Creating food array with id
				ArrayList<String> a = DataManager.getFoodIDfromFoodName(foodArray);
				String[] idArray = new String[foodArray.length];
				int i = 0;
				for(String s : a){
					idArray[i]=s;
					i++;
				}
				if(DataManager.saveMealPlan(user, inputDate, foodArray, amountArray)){
					response.sendRedirect("MealPlanServlet?VARIABLE=VARVALUE");
				}else{
					System.out.println("PROBLEMS!");
				}
			} else{
				//TODO WRONG FOOD ARRAYS
			}
		} else {
			//TODO MISSING DATE
		}
		
		
    }
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		        throws ServletException, IOException{
		
			
	}
}

