package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;

public class Servlet extends HttpServlet {       

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterNames().nextElement()=="usernameLogin"){
			//Login triggered
			
			String username = request.getParameter("usernameLogin");
			String password = request.getParameter("passwordLogin");
			//TODO DoubleCheck username and password 
			
			if(username != null && password != null){
				//TODO Check credentials in the database
				
				Boolean  userExists = true;
				if (userExists){
					//Create bean
					UserBean user = new UserBean();
					user.setUsernameLogin("username");
					user.setPasswordLogin("password");
					HttpSession session = request.getSession();
					session.setAttribute("uBean", user);		//codice per creare un bean e caricarlo
					request.getRequestDispatcher("/Profile.jsp").forward(request, response);//Send profile page
				} else {
					//Send back again the login page with the error "Wrong UserName or Password"
					request.getRequestDispatcher("/LoginForm.html?ERROR=WRONG").forward(request, response);
				}
				
			} else {
				//Send back again the login page with the error "missing parameters"
				request.getRequestDispatcher("/LoginForm.html?ERROR=MISSING").forward(request, response);
			}
			
			//UserBean user = (UserBean) session.getAttribute("uBean");		//codice per prendersi un bean gia` creato
			//PrintWriter writer = response.getWriter();
			//writer.print("<html><body>" + username + " " + password + "</body></html>");
			
		} else {
			//Subscription triggered
			
			String username = request.getParameter("usernameReg");
			String password1 = request.getParameter("passwordReg1");
			String password2 = request.getParameter("passwordReg2");
			String email = request.getParameter("email");
			
			
			if(username != null && password1 != null && password2 != null && email != null){

				//TODO DoubleCheck username and login 
				

				//TODO Check credentials in the database
				
				Boolean  userDoesntExist = false;
				if (userDoesntExist){	
					if(password1.compareTo(password2)==0){
						
						//Create bean
						UserBean user = new UserBean();
						user.setUsernameLogin("username");
						user.setPasswordLogin("password");
						HttpSession session = request.getSession();
						session.setAttribute("uBean", user);		//codice per creare un bean e caricarlo
						request.getRequestDispatcher("/Profile.jsp").forward(request, response);//Send profile page
						
					} else {
						request.getRequestDispatcher("/LoginForm.html?ERROR=PASS_MATCH").forward(request, response);
					}
					
				} else {
					//Send back again the login page with the error "Wrong UserName or Password"
					request.getRequestDispatcher("/LoginForm.html?ERROR=USER_EXISTS").forward(request, response);
				}
				
			} else {
				//Send back again the login page with the error "missing parameters"
				request.getRequestDispatcher("/LoginForm.html?ERROR=MISSING_PARAM").forward(request, response);
			}
		}
		
	}
}
