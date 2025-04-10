package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class register extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String id = request.getParameter("id");
        String pwd = request.getParameter("pin");
        String phone = request.getParameter("phone");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String ifsc = request.getParameter("ifsc");
        String city = request.getParameter("city");
        String amt = request.getParameter("amt");

        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/saka_atm_db", "root", "root");
                 PreparedStatement ps = c.prepareStatement("INSERT INTO account_holder (account_no, fullname, email, pin, phone, dob, gender, ifse_code, address, amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                
                ps.setString(1, id);
                ps.setString(2, fullname);
                ps.setString(3, email);
                ps.setString(4, pwd);
                ps.setString(5, phone);
                ps.setString(6, dob);
                ps.setString(7, gender);
                ps.setString(8, ifsc);
                ps.setString(9, city);
                ps.setString(10, amt);

                int value = ps.executeUpdate();
                
                out.println("<script type='text/javascript'>");
                if (value == 1) {
                    out.println("alert('Registration Successful!');");
                    out.println("alert('Remember Your Credentials:\\nAccount No: " + id + "\\nPIN: " + pwd + "');");
                } else {
                    out.println("alert('Registration Failed. Please try again.');");
                }
                out.println("window.location.href='index.html';");
                out.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script type='text/javascript'>");
            out.println("alert('An error occurred. Please try again later.');");
            out.println("window.location.href='index.html';");
            out.println("</script>");
        }
    }
}
								



//
//package com;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.Statement;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//@WebServlet("/register")
//public class register extends HttpServlet {
//	
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		
//		String fullname=request.getParameter("fullname");
//		String email=request.getParameter("email");
//		String id=request.getParameter("id");
//		String pwd=request.getParameter("pin");
//		String phone=request.getParameter("phone");
//		String dob=request.getParameter("dob");
//		String gender=request.getParameter("gender");
//		String ifsc=request.getParameter("ifsc");
//		String city=request.getParameter("city");
//		String amt=request.getParameter("amt");
//		
//		PrintWriter out = response.getWriter();
//		
//    	try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			
//			Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/saka_atm_db","root","root");
//		
//			Statement s=c.createStatement();
//			int value=s.executeUpdate("insert into account_holder(account_no,fullname,email,pin,phone,dob,gender,ifse_code,address,amount) values('"+id+"','"+fullname+"','"+email+"','"+pwd+"','"+phone+"','"+dob+"','"+gender+"','"+ifsc+"','"+city+"','"+amt+"')");
//			
//			if(value == 1) {
//				System.out.println("row affected");
//				out.println("<script type='text/javascript'>");
//				
//				out.println("alert('Welcome..');");
//				out.println("alert('Remember Your Credentials:\\nAccount No: " + id + "\\nPIN: " + pwd + "');");
//				out.println("window.location.href='index.html';"); // Redirect to index.html after the alert
//				out.println("</script>");
//			}
//			else {
//				System.out.println("not affected");
//				out.println("<script type='text/javascript'>");
//				out.println("alert('not regitored');");
//				out.println("window.location.href='index.html';"); // Redirect to index.html after the alert
//				out.println("</script>");
//			}
//			
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		
//	}
//
//}
