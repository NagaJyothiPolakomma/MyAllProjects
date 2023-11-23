package com.jyo;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import org.apache.synapse.MessageContext; 
import org.apache.synapse.mediators.AbstractMediator;

public class JsonResponse extends AbstractMediator
{ 
	public boolean mediate(MessageContext mc) 

	{
		 String url = "jdbc:mysql://localhost:3306/vyshu";
	        String username = "root";
	        String password = "Jyothi@2000";

	        try {
	            
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection(url, username, password);
	            String sql = "INSERT INTO storeresponseoutput (id,response) VALUES(?,?)";
	            PreparedStatement pre = con.prepareStatement(sql);
	            String id=(String) mc.getProperty("id");
           String  jsonData = (String) mc.getProperty("mySql");
           pre.setString(1, id);
	            pre.setString(2, jsonData);
            int key = pre.executeUpdate();

	            if (key > 0) 
	            {
	                System.out.println("JSON data inserted successfully.");
	            } else {
	                System.out.println("Failed to insert JSON data.");
	            }

	         
	            pre.close();
	            con.close();

	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        } 
	        catch (SQLException e)
	        {
	            e.printStackTrace();
	        }
			return true;
	}
	

	}


