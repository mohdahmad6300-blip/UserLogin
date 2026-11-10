package com.servlets;
import com.service.SignUpService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        SignUpService service = new SignUpService();
        
        if(service.signUp(username, email, password, role))
        {
        	out.println("<script>");
            out.println("alert('Successfully Registered!');");
            out.println("window.location='Index.html';");
            out.println("</script>");
        }
        else
        {
        	out.print("Error! Enter Data in Wrong Format.");
        }

	
	}
}