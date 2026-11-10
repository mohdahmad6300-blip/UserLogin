package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


public class SignUpService 
{
	public boolean signUp(String name, String email, String password, String role)
	{
        
        try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement","root","ahmad@632");	
			
			PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)");
			
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);
			ps.setString(4, role);
			int result = ps.executeUpdate();
			
			ps.close();
	        con.close();

	        if (result > 0) {
	            return true;
	        }

		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		
		return false;
	}
	
}
