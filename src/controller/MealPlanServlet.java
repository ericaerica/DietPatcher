package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import database.DataManager;

@WebServlet("/MealPlanServlet")
public class MealPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       System.out.println("IM IN");
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
       
        JSONArray arrayObj=new JSONArray();
       
        String query = request.getParameter("term");
        System.out.println(query);
        query = query.toLowerCase();
        
        ArrayList<String> list = DataManager.getFood(query);
        
        for(String s : list) {
             ((List<String>) arrayObj).add(s); 
        }
       
        out.println(arrayObj.toString());
        out.close();
       
    }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
