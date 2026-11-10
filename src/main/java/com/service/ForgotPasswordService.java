package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ForgotPasswordService 
{
	public boolean forgot(String email , String password)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement","root","ahmad@632");	
			
			PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
			
			ps.setString(1, password);
			ps.setString(2, email);
			
			int rows = ps.executeUpdate();
			
			if (rows>0)
			{return true; }
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
