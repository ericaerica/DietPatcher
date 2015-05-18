package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.DataManager;
import model.ProfileUtils;
import model.UserBean;

import java.io.IOException;

public class ProfileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
			

			String username = request.getParameter("profileUserName");
			String password = request.getParameter("profilePassword");
			String email = request.getParameter("profileEmail");
			String gender = request.getParameter("profileGender");
			int age = Integer.parseInt(request.getParameter("profileAge"));
			double height = Double.parseDouble(request.getParameter("profileHeight"));
			double weight = Double.parseDouble(request.getParameter("profileWeight"));
			double waist = Double.parseDouble(request.getParameter("profileWaist"));
			
			
			//Check if null parameters
			if(username != "" 
					&& password != "" 
					&& email !=""
					&& email != null
					&& username != null 
					&& password != null){
				//Check if valid strings
				if(ProfileUtils.isUsernameValid(username) 
						&& ProfileUtils.isPasswordValid(password)
						&& ProfileUtils.isEmailValid(email)
						){
					//Try to get user
					DataManager.connect();
					UserBean user = new UserBean();
					user.setUserBeanParameters(email, username, password, gender, age, height, weight, waist);
					DataManager.saveUser(user);

					HttpSession session = request.getSession();
					session.setAttribute("uBean", user);
					request.getRequestDispatcher("/Profile.jsp").forward(request, response);
					
				} else {
					//Send back again the profile page with the error "Invalid input"
					response.sendRedirect("ProfileServlet?ERROR=INVALID");
				}
				
				
			} else {
				//Send back again the profile page with the error "missing parameters"
				response.sendRedirect("ProfileServlet?ERROR=MISSING");
			}	
	} 
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
		

