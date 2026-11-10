package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.service.ForgotPasswordService;

@WebServlet("/ForgotPassword")
public class ForgotPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        
        String email = request.getParameter("email");
        String pass =  request.getParameter("password");
     
        ForgotPasswordService password = new ForgotPasswordService();
        
        if(password.forgot(email, pass))
        {
        	out.println("<script>");
            out.println("alert('Password Updated Successfully!');");
            out.println("window.location='Index.html';");
            out.println("</script>");
        }
        else
        {
        	out.println("<script>");
            out.println("alert('Enter Registers Email Only!');");
            out.println("</script>");
        }
        
	}

}
