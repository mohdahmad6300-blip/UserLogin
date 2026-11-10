package com.servlets;
import com.service.LoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginPage")
public class LoginPage extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");		
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		LoginService service = new LoginService();
		
		if(service.isValidUserAndPassword(email, password))
		{
			
			HttpSession session = request.getSession();
			session.setAttribute("loggedInEmail", email);
			
			response.sendRedirect("DashBoard");
		}
		else
		{
			response.sendRedirect("ErrorPage");
		}
	}	
}