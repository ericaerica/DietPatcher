package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import model.UserBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import database.DataManager;

public class MealPlanDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control",
				"no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		HttpSession session = request.getSession(false);
		UserBean usr = (session != null) ? (UserBean) session
				.getAttribute("uBean") : null;
		if (usr == null) {
			response.sendRedirect("LoginForm.html"); // No logged-in user found,
														// so redirect to login
														// page.
		} else {
			// 1. get received JSON data from request
			BufferedReader br = new BufferedReader(new InputStreamReader(
					request.getInputStream()));
			String json = "";
			if (br != null) {
				json = br.readLine();
			}

			// 2. initiate jackson mapper
			ObjectMapper mapper = new ObjectMapper();

			// 3. Convert received JSON to String
			String date = mapper.readValue(json, String.class);

			String s = "";

			// If date matches the regex
			if (date.matches("^([0]?[1-9]|[1][0-2])[/]([0]?[1-9]|[1|2][0-9]|[3][0|1])[/]([0-9]{4}|[0-9]{2})$")) {
				// 4. Go get the meal plan table
				UserBean user = (UserBean) request.getSession().getAttribute(
						"uBean");
				ArrayList<String[]> output = DataManager.getMealPlanFromDate(
						user, date);

				if (!output.isEmpty()) {// if there's a mealplan

					String[] foodId = output.get(0);
					ArrayList<String> foodDesc = DataManager
							.getFoodNameFromFoodId(foodId);
					String[] foodAmount = output.get(1);
					for (int i = 0; i < foodId.length; i++) {
						s += "<tr><td>"
								+ foodDesc.get(i)
								+ "<input name='food_name' value='"
								+ foodId[i]
								+ "' type='hidden'></td><td>"
								+ foodAmount[i]
								+ "<input name='food_amount' value='"
								+ foodAmount[i]
								+ "' type='hidden'></td><td><span style='color:#a00; cursor:pointer;' class='glyphicon glyphicon-remove' aria-hidden='true' onclick='del(this);'></span></td></tr>";
					}

				}
			} else {//If it ain't no Date
				s += "<div class='alert alert-danger' role='alert'>You inserted a wrongly formatted Date, please use the format MM/DD/YYYY</div>";
			}

			// 5. Set response type to JSON
			response.setContentType("application/json");

			// 6. Send the string as JSON to client
			mapper.writeValue(response.getOutputStream(), s);
		}
	}
}
