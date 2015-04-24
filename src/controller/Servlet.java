package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class Servlet extends HttpServlet {       

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean user = new UserBean();
		session.setAttribute("uBean", user);		//codice per creare un bean e caricarlo (in teoria)
		//UserBean user = (UserBean) session.getAttribute("uBean");		//codice per prendersi un bean gia` creato
		String username = request.getParameter("usernameLogin");
		PrintWriter writer = response.getWriter();
		writer.print("<html><body>" + user.getUsernameLogin() + " " + username + "</body></html>");
		//if(username.equals("erica")){
			//request.getRequestDispatcher("/Profile.jsp").forward(request, response);
		//}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
