<%@ page import="java.sql.*" %>
<%
    // Get the username (uname) from the URL parameters
    String uname = request.getParameter("id");
    String fullname = ""; // Default value if no user is found
    String acc_no = "";
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
                acc_no=rs.getString("account_no");
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
    <title>ATM Dashboard</title>
    <style>
        /* Your existing CSS styles */
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(45deg, #E3E3E3, #A7A7A7);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 0;
        }
        .atm-container {
            display: flex;
            width: 800px;
            height: 500px;
            background: #003087;
            color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
        }
        .left-panel {
            width: 30%;
            background: #002366;
            padding: 20px;
        }
        .left-panel h2 {
            margin: 0;
            font-size: 22px;
            font-weight: bold;
        }
        .left-panel p {
            margin: 20px 0;
            font-size: 16px;
        }
        .left-panel strong {
            font-size: 22px;
        }
        .right-panel {
            width: 70%;
            padding: 30px;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .button-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 15px;
        }
        .atm-button {
            background: #009BFF;
            color: white;
            font-size: 18px;
            padding: 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: 0.3s;
        }
        .atm-button:hover {
            background: #0820fc;
        }
        .quick-cash {
            background: #E40046;
            color: white;
            font-size: 20px;
            padding: 15px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: left;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-weight: bold;
            padding: 15px 20px;
            width: 100%;
        }
        .quick-cash span {
            font-size: 24px;
        }
    </style>
</head>
<body>
    <div class="atm-container">
        <div class="left-panel">
            <h2>ATM</h2>
            <p>Welcome <br><strong><%= fullname %></strong></p>
            <p>Account No<br><strong><%= acc_no %></strong></p>
            <p>Saving<br><strong><%= amount %></strong></p>
        </div>
        <div class="right-panel">
            <div class="button-grid">
                <button class="atm-button" onclick="location.href='deposit.html'">Deposit</button>
                <button class="atm-button" onclick="location.href='withdraw.html'">Withdraw</button>
                <button class="atm-button">Transfer</button>
                <button class="atm-button other">Other</button>
            </div>
            <button class="quick-cash">
                <span>Welcome</span> Quick Cash →
            </button>
        </div>
    </div>
</body>
</html>
