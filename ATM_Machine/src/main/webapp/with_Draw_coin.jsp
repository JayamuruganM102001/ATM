<%@ page import="java.sql.*" %>
<%
    // Get the username (uname) from the URL parameters
    String uname = request.getParameter("id");
    String fullname = ""; // Default value if no user is found
    String damt = request.getParameter("wamount");;
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
    <title>Transaction Confirmed</title>
    <script src="https://unpkg.com/@tailwindcss/browser@4"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background: linear-gradient(120deg, #2980b9, #8e44ad);
            font-family: 'Roboto', sans-serif;
            color: #fff;
            overflow: hidden;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            width: 80%;
            max-width: 800px;
        }

        .card {
            width: 350px;
            height: 550px;
            background: rgba(255, 255, 255, 0.1);
            border-radius: 15px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            text-align: center;
            padding: 20px;
            transition: transform 1.5s ease-in-out;
            position: absolute;
        }

        .atm-card {
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .atm-card img {
            width: 100%;
            height: auto;
            border-radius: 10px;
        }

        .transaction-card {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }

        .message {
            font-size: 22px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .details {
            font-size: 15px;
            color: #2e2d2d;
        }

        .container.animate .card:nth-child(1) {
            transform: translateX(-200px);
        }

        .container.animate .card:nth-child(2) {
            transform: translateX(200px);
        }

        body {
            font-family: 'Inter', sans-serif;
            /*background-color: #001f3f; /* Navy Blue Background */
            background: linear-gradient(120deg, #00155f, #8102b8);
        }

        .money-animation {
            position: relative;
            width: 200px;
            height: 150px;
            margin: 2rem auto;
        }

        .coin {
            position: absolute;
            width: 30px;
            height: 30px;
            border-radius: 50%;
            background-color: #cbd655;
            color: #1e293b;
            font-size: 0.75rem;
            display: flex;
            align-items: center;
            justify-content: center;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
            animation: money-fall 3s ease-in-out infinite;
            top: -30px;
        }

        .coin svg {
            width: 60%;
            height: 60%;
        }

        @keyframes money-fall {
            0% {
                transform: translateY(0) rotate(0deg);
                opacity: 0;
            }
            10% {
                opacity: 0.8;
            }
            70% {
                opacity: 1;
            }
            100% {
                transform: translateY(180px) rotate(360deg);
                opacity: 0;
            }
        }

        .coin:nth-child(1) { left: 10%; animation-delay: 0s; }
        .coin:nth-child(2) { left: 30%; animation-delay: 0.2s; }
        .coin:nth-child(3) { left: 50%; animation-delay: 0.4s; }
        .coin:nth-child(4) { left: 70%; animation-delay: 0.6s; }
        .coin:nth-child(5) { left: 20%; animation-delay: 0.8s; }
        .coin:nth-child(6) { left: 60%; animation-delay: 1.0s; }
    </style>

    </style>
</head>
<body>
    <div class="container" id="cardContainer">
        <div class="card atm-card" style="background-color: #fff;">
            <!-- <img src="https://via.placeholder.com/300x200?text=ATM+Card" alt="ATM Card"> -->
            
            <img src="image/coin.png" alt="ATM Card">
            
            
        </div>
        <div class="card transaction-card" style="background-color: #fff;">
            <div class="checkmark-circle relative w-32 h-32 mx-auto mb-6">
                <svg class="checkmark absolute top-0 left-0 w-full h-full" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100">
                    <path class="checkmark__check icon" fill="none" stroke="#34d399" stroke-width="8" d="M20 60 L40 80 L80 30" />
                </svg>
                <style>
                    .checkmark-circle {
                        border-radius: 50%;
                        background-color: #c5d4cc;
                        border: #001f3f 2px solid;
                        height: 100px;
                        width: 100px;
                        display: flex;
                        justify-content: center;
                        align-items: center;
                       
                        
                    }
                    .checkmark__check {
                        stroke-dasharray: 100;
                        stroke-dashoffset: 100;
                        animation: checkmark 2s ease-in-out forwards;
                    }
                    @keyframes checkmark {
                        0% { stroke-dashoffset: 100; }
                        100% { stroke-dashoffset: 0; }
                    }
                    
                </style>
            </div>
            <h2 class="text-2xl font-semibold text-green-600 mb-4">Success!</h2>
        <p class="text-gray-700 mb-6">Your Withdrawal was successful.</p>
            <div class="details">
                <p><strong><%= fullname %></strong></p>
                <p>Withdrawal Amount : <strong><%= damt %></strong></p>
                <p>Current Balance : <strong><%= amount %></strong></p>
            </div>
            
            <div class="money-animation">
                <div class="coin">$</div>
                <div class="coin">$</div>
                <div class="coin">$</div>
                <div class="coin">$</div>
                <div class="coin">$</div>
                <div class="coin">$</div>
                
            </div>
        </div>
    </div>

    <script>
        setTimeout(() => {
            document.getElementById('cardContainer').classList.add('animate');
        }, 2000);
    </script>
</body>
</html>
