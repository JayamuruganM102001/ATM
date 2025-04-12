package com;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deposit")
public class deposit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String acc_no = request.getParameter("id");
        String amtStr = request.getParameter("damount");
        String pass = request.getParameter("pword");

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/saka_atm_db", "root", "root");

            // Check if the account exists
            String checkQuery = "SELECT * FROM account_holder WHERE account_no = ? AND pin = ?";
            ps = conn.prepareStatement(checkQuery);
            ps.setString(1, acc_no);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                double o_amt = rs.getDouble("amount");  // Fetch current balance
                double amt = Double.parseDouble(amtStr); // Convert deposit amount

                double newAmount = o_amt + amt; // Add the amounts

                // Update the new balance in the database
                String updateQuery = "UPDATE account_holder SET amount = ? WHERE account_no = ?";
                ps = conn.prepareStatement(updateQuery);
                ps.setDouble(1, newAmount);
                ps.setString(2, acc_no);
                
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
//                    response.getWriter().write("Deposit Successful! New Balance: $" + newAmount);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("coin_dep.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.getWriter().write("Deposit Failed!");
                }
            } else {
                response.getWriter().write("Account number or PIN is incorrect!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (ps != null) ps.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
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
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/deposit")
//public class deposit extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String uid = request.getParameter("id");
//        String psd = request.getParameter("pword");
//        String amt = request.getParameter("damount");
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
//                    	sendAlert(response, "enter");
//                    	System.out.println(amt);
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
