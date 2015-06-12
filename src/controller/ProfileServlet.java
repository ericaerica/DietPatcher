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
import java.util.ArrayList;

public class ProfileServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		HttpSession session = request.getSession(false);
	    UserBean usr = (session != null) ? (UserBean) session.getAttribute("uBean") : null;
	    if (usr == null) {
	        response.sendRedirect("LoginForm.html"); // No logged-in user found, so redirect to login page.
	    }else{

		String username = request.getParameter("profileUserName");
			String password = request.getParameter("profilePassword");
			String email = request.getParameter("profileEmail");
			String gender = request.getParameter("profileGender");
			int age = Integer.parseInt(request.getParameter("profileAge"));
			double height = Double.parseDouble(request.getParameter("profileHeight"));
			double weight = Double.parseDouble(request.getParameter("profileWeight"));
			double waist = Double.parseDouble(request.getParameter("profileWaist"));
			String[] tagArray = request.getParameterValues("tag");
			ArrayList<String> tags = new ArrayList<String>();

			//Prepare the ArrayList of user tags
			if(tagArray != null && tagArray.length > 0){
				for(int i = 0; i < tagArray.length; i++){
					tags.add(tagArray[i]);
				}
			}
			
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
					DataManager.saveTags(user, tags);
					user.setTags(DataManager.getTags(user));
					session = request.getSession();
					session.setAttribute("uBean", user);
					request.getRequestDispatcher("Redirect?page=Profile").forward(request, response);
					
				} else {
					//Send back again the profile page with the error "Invalid input"
					response.sendRedirect("ProfileServlet?ERROR=INVALID");
				}
				
			} else {
				//Send back again the profile page with the error "missing parameters"
				response.sendRedirect("ProfileServlet?ERROR=MISSING");
			}	}
	} 
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
		

