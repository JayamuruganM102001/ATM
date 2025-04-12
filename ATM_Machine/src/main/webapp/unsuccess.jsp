<%@ page import="java.sql.*" %>
<%
    // Get the username (uname) from the URL parameters
    String uname = request.getParameter("id");
    String fullname = ""; // Default value if no user is found
    String amt = request.getParameter("wamount");;
    String amount = "";

    if (uname != null && !uname.isEmpty()) {
        try {
            // Database connection details
            String url = "jdbc:mysql://localhost:3306/saka_atm_db"; // Change 'your_database'
            String user = "root"; // Change 'your_db_user'
            String password = "root"; // Change 'your_db_password'

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            
            // Query to fetch fullname based on username
            String query = "SELECT fullname,account_no,amount FROM account_holder WHERE account_no = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, uname);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                fullname = rs.getString("fullname");
                
                amount=rs.getString("amount");
                
            }

            // Close connections
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Withdrawal Declined</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(120deg, #ff4e50, #f9d423);
            font-family: 'Inter', sans-serif;
            color: #fff;
            margin: 0;
        }
        .card {
            width: 350px;
            padding: 20px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 15px;
            text-align: center;
            animation: shake 0.5s ease-in-out 2;
        }
        .cross-mark {
            width: 80px;
            height: 80px;
            background: #ff0000;
            border-radius: 50%;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0 auto 20px;
            border: 4px solid white;
            position: relative;
        }
        .cross-mark::before,
        .cross-mark::after {
            content: '';
            position: absolute;
            width: 50px;
            height: 6px;
            background: white;
        }
        .cross-mark::before {
            transform: rotate(45deg);
        }
        .cross-mark::after {
            transform: rotate(-45deg);
        }
        @keyframes shake {
            0% { transform: translateX(0); }
            25% { transform: translateX(-5px); }
            50% { transform: translateX(5px); }
            75% { transform: translateX(-5px); }
            100% { transform: translateX(0); }
        }
        .retry-btn {
            margin-top: 20px;
            padding: 10px 20px;
            background: #ff0000;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
            cursor: pointer;
            transition: 0.3s;
        }
        .retry-btn:hover {
            background: #cc0000;
        }
    </style>
</head>
<body>
    <div class="card">
        <div class="cross-mark"></div>
        <h2>Transaction Declined</h2>
        <p>Your withdrawal could not be processed. Please check your balance and try again.</p>
        <p><strong><%= fullname %></strong></p>
        <p>Your Current Balance : <strong><%= amount %></strong></p>
        <button class="retry-btn" onclick="window.location.href='withdraw.html'">Retry</button>
    </div>
</body>
</html>
