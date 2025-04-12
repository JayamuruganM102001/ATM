//package com;
//


package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/demo")
public class demo extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // MySQL credentials
    private static final String DB_URL = "jdbc:mysql://localhost:3306/saka_atm_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNo = request.getParameter("id");
        String enteredPin = request.getParameter("pword");

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB and prepare statement
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                 PreparedStatement ps = conn.prepareStatement("SELECT pin FROM account_holder WHERE account_no = ?")) {

                ps.setString(1, accountNo);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String correctPin = rs.getString("pin");

                    if (enteredPin.equals(correctPin)) {
                        // PIN matched – forward to dashboard
                        RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
                        rd.forward(request, response);
                    } else {
                        // Incorrect PIN
                        sendAlert(out, "Invalid PIN. Please try again.");
                    }
                } else {
                    // No account found
                    sendAlert(out, "No user found with this Account Number.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                sendAlert(out, "Database error occurred.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendAlert(PrintWriter out, String message) {
        out.println("<script type='text/javascript'>");
        out.println("alert('" + message + "');");
        out.println("window.location.href='index.html';");
        out.println("</script>");
    }
}





//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/demo")
//public class demo extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String uid = request.getParameter("id");
//        String psd = request.getParameter("pword");
//
//        try (PrintWriter out = response.getWriter()) {
//            // Load JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/saka_atm_db", "root", "root");
//                 PreparedStatement statement = connection.prepareStatement("SELECT pin FROM account_holder WHERE account_no = ?")) {
//
//                statement.setString(1, uid);
//                ResultSet resultSet = statement.executeQuery();
//
//                if (resultSet.next()) {
//                    String storedPassword = resultSet.getString("pin");
//
//                    if (psd.equals(storedPassword)) {
//                        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
//                        dispatcher.forward(request, response);
//                    } else {
//                        sendAlert(response, "Invalid PIN. Please try again.");
//                    }
//                } else {
//                    sendAlert(response, "No user found with this Account Number.");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void sendAlert(HttpServletResponse response, String message) throws IOException {
//        try (PrintWriter out = response.getWriter()) {
//            out.println("<script type='text/javascript'>");
//            out.println("alert('" + message + "');");
//            out.println("window.location.href='index.html';");
//            out.println("</script>");
//        }
//    }
//}
//
//
////package com;
////
////import java.io.IOException;
////import java.io.PrintWriter;
////import java.sql.Connection;
////import java.sql.DriverManager;
////import java.sql.PreparedStatement;
////import java.sql.ResultSet;
////
////import javax.servlet.RequestDispatcher;
////import javax.servlet.ServletException;
////import javax.servlet.annotation.WebServlet;
////import javax.servlet.http.HttpServlet;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////
////@WebServlet("/demo")
////public class demo extends HttpServlet {
////    private static final long serialVersionUID = 1L;
////
////    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        String uid = request.getParameter("id");
////        String psd = request.getParameter("pword");
////        
////        PrintWriter out = response.getWriter();
////        out.println("alert('hello');");
////
////        try {
////            Class.forName("com.mysql.cj.jdbc.Driver");
////
////            try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/saka_atm_db", "root", "root");
////                 PreparedStatement ps = c.prepareStatement("SELECT pin FROM account_holder WHERE account_no = ?")) {
////
////                ps.setString(1, uid);
////                ResultSet rs = ps.executeQuery();
////
////                
////
////                if (rs.next()) {
////                    String storedPassword = rs.getString("pin");
////
////                    if (psd.equals(storedPassword)) { // TODO: Use hashed password check instead
////                        request.getRequestDispatcher("dashboard.html").forward(request, response);
////                    } else {
////                        sendAlert(out, response, "Invalid PIN. Please try again.");
////                    }
////                } else {
////                    sendAlert(out, response, "No user found with this Account Number.");
////                }
////            }
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////
////    private void sendAlert(PrintWriter out, HttpServletResponse response, String message) throws IOException {
////        out.println("<script type='text/javascript'>");
////        out.println("alert('" + message + "');");
////        out.println("window.location.href='index.html';");
////        out.println("</script>");
////    }
////}
////
//
//
////package com;
////
////import java.io.IOException;
////import java.io.PrintWriter;
////import java.sql.Connection;
////import java.sql.DriverManager;
//////import java.sql.PreparedStatement;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////import java.sql.Statement;
////
////import javax.servlet.RequestDispatcher;
////import javax.servlet.ServletException;
////import javax.servlet.annotation.WebServlet;
////import javax.servlet.http.HttpServlet;
////import javax.servlet.http.HttpServletRequest;
////import javax.servlet.http.HttpServletResponse;
////
//////import com.mysql.cj.xdevapi.Statement;
////
////
////@WebServlet("/demo")
////public class demo extends HttpServlet {
////	private static final long serialVersionUID = 1L;
////       
////   
////	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////		
////		String uid=request.getParameter("id");
////		String psd=request.getParameter("pword");
////		
////		
////		try {
////			Class.forName("com.mysql.cj.jdbc.Driver");
////			Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/saka_atm_db","root","root");
////			Statement s=c.createStatement();
////			
////			ResultSet rs ;
////			
////			PrintWriter out = response.getWriter();
////			
////
////			
////				
////			rs = s.executeQuery("select * from register");
////			while(rs.next()) {
////				String id = rs.getString("account_no");
////				String pword = rs.getString("pin");
////				if(uid.equals(id) && psd.equals(pword)) {
////					out.println("welcome user......");
////					RequestDispatcher rd = request.getRequestDispatcher("dashboard.html");
////					rd.forward(request, response);
////					return;
////				}
////				
////			}
////			out.println("<script type='text/javascript'>");
////			out.println("alert('No user found');");
////			out.println("window.location.href='index.html';"); // Redirect to index.html after the alert
////			out.println("</script>");
////			
////
////			} 
////		
////		catch (ClassNotFoundException e) {
////		
////			e.printStackTrace();
////		} catch (SQLException e) {
////			e.printStackTrace();
////		}
////		
////	}
////
////	
////	
////
////}
//
//
////if(username.equals("admin") && password.equals("admin")) {
////rs = s.executeQuery("SELECT a.date, r.fullname, a.In_time, a.Out_time \r\n"
////		+ "FROM registor r \r\n"
////		+ "JOIN attand a \r\n"
////		+ "ON r.uid = a.fullname;");
////response.setContentType("text/html");
////
////
////out.println("<style>");
////out.println("table { width: 80%; border-collapse: collapse; margin: 20px auto; font-family: Arial, sans-serif; }");
////out.println("th, td { padding: 10px; border: 1px solid #ddd; text-align: center; }");
////out.println("th { background-color: #007BFF; color: white; }");
////out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
////out.println("tr:hover { background-color: #ddd; }");
////out.println("</style>");
////
////
////out.println("<input type='date' id='int' name='int' required>");
////out.println("<input type='date' id='outt' name='outt' required>");
////out.println("<button type='submit' class='btn' id='btn1'>filter</button>");
////out.println("<h2 style='text-align:center;'>Attendance</h2>");
////out.println("<table>");
////out.println("<tr><th>Date</th><th>Name</th><th>In Time</th><th>Out Time</th></tr>");
////
////while (rs.next()) {
////    out.println("<tr><td>" + rs.getString("date") + "</td><td>" + rs.getString("fullname") + "</td><td>" + rs.getString("In_time") + "</td><td>" + rs.getString("Out_time") + "</td></tr>");
////}
////
////out.println("</table>");
