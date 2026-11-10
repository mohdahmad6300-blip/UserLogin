package com.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginService 
{
	public boolean isValidUserAndPassword(String email, String pass)
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/usermanagement","root","ahmad@632");	
			
			PreparedStatement ps = con.prepareStatement("select * from users where email = ? and password = ?");
			
			ps.setString(1, email);
			ps.setString(2, pass);
			
			ResultSet rs = ps.executeQuery();

			if(rs.next())
			{
			    System.out.println("USER FOUND");
			    return true;
			}
			else
			{
			    System.out.println("USER NOT FOUND");
			    return false;
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();	
		}
		
		return false;
	}
	
}

