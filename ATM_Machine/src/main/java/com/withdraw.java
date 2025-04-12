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

@WebServlet("/withdraw")
public class withdraw extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String acc_no = request.getParameter("id");
        String amtStr = request.getParameter("wamount");
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
                
                if(amt>o_amt) {
//                	response.getWriter().write("Insufficient bank balance\n");
//                	response.getWriter().write("Current Bank Balance : "+o_amt);
                	RequestDispatcher dispatcher = request.getRequestDispatcher("unsuccess.jsp");
                    dispatcher.forward(request, response);
                	return;
                }
                double newAmount = o_amt - amt; // Add the amounts

                // Update the new balance in the database
                String updateQuery = "UPDATE account_holder SET amount = ? WHERE account_no = ?";
                ps = conn.prepareStatement(updateQuery);
                ps.setDouble(1, newAmount);
                ps.setString(2, acc_no);
                
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
//                    response.getWriter().write("withdraw Successful! New Balance: $" + newAmount);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("with_Draw_coin.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.getWriter().write("withdraw Failed!");
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
