package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import database.DataManager;
import model.ProfileUtils;
import model.UserBean;

import java.io.IOException;

public class Servlet extends HttpServlet {       

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameterNames().nextElement()=="usernameLogin"){
			
			//-----------------------------------------------
			//-----------------Login triggered---------------
			//-----------------------------------------------
			
			String username = request.getParameter("usernameLogin");
			String password = request.getParameter("passwordLogin");
			
			//Check if null parameters
			if(username != null && password != null){
				
				//Check if valid strings
				if(ProfileUtils.isUsernameValid(username) && ProfileUtils.isPasswordValid(password)){
					
					//Try to get user
					UserBean user = DataManager.getUser(username,password);
					
					//Check if user exists
					if (user!=null){
						//If exists, create bean
						HttpSession session = request.getSession();
						session.setAttribute("uBean", user);
						request.getRequestDispatcher("/Profile.jsp").forward(request, response);
					} else {
						//Send back again the login page with the error "Wrong UserName or Password"
						request.getRequestDispatcher("/LoginForm.html?ERROR=WRONG").forward(request, response);
					}
					
				} else {
					//Send back again the login page with the error "Invalid input"
					request.getRequestDispatcher("/LoginForm.html?ERROR=INVALID").forward(request, response);
				}
				
				
			} else {
				//Send back again the login page with the error "missing parameters"
				request.getRequestDispatcher("/LoginForm.html?ERROR=MISSING").forward(request, response);
			}
			
			//UserBean user = (UserBean) session.getAttribute("uBean");		//codice per prendersi un bean gia` creato
			//PrintWriter writer = response.getWriter();
			//writer.print("<html><body>" + username + " " + password + "</body></html>");
			
		} else if (request.getParameterNames().nextElement()=="usernameReg"){
			
			//-----------------------------------------------
			//------------Subscription triggered-------------
			//-----------------------------------------------
			
			String username = request.getParameter("usernameReg");
			String password1 = request.getParameter("passwordReg1");
			String password2 = request.getParameter("passwordReg2");
			String email = request.getParameter("email");
			
			//Check if any of the parameters are null
			if(username != null && password1 != null && password2 != null && email != null){

				//Check if valid
				if(ProfileUtils.isUsernameValid(username) 
						&& ProfileUtils.isPasswordValid(password1) 
						&& ProfileUtils.isPasswordValid(password2)
						&& ProfileUtils.isEmailValid(email)){
					
					//Check if passwords are equal
					if(password1.equals(password2)){
						
						//Check if account exists:
						//Try to get user
						UserBean user = DataManager.getUser(username,password1);
						
						//If the user doesn't exist
						if (user==null){
							
							user = new UserBean();
							user.setUsernameLogin(username);
							user.setPassword(password1);
							user.setEmail(email);
							
							DataManager.saveUser(user);
							
							HttpSession session = request.getSession();
							session.setAttribute("uBean", user);
							
							
						} else {
							//Send back again the login page with the error "A user with the same username or password already exist"
							request.getRequestDispatcher("/LoginForm.html?ERROR=USER_EXISTS").forward(request, response);
						}
						
					} else {
						//Send back again the login page with the error "Password mismatch"
						request.getRequestDispatcher("/LoginForm.html?ERROR=PASS_MIS").forward(request, response);
					}
					
				} else {
					//Send back again the login page with the error "Invalid input"
					request.getRequestDispatcher("/LoginForm.html?ERROR=INVALID").forward(request, response);
				}
				
			} else {
				//Send back again the login page with the error "missing parameters"
				request.getRequestDispatcher("/LoginForm.html?ERROR=MISSING_PARAM").forward(request, response);
			}
		} else {
			//TODO Implement case of error made by user in LoginForm.html
		}
		
	}
}
