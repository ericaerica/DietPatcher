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

				if(DataManager.saveMealPlan(user, inputDate, foodArray, amountArray)){
					request.getRequestDispatcher("/MealPlan.jsp").forward(request, response);
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

