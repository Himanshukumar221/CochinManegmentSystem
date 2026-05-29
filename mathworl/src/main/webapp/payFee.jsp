<%@ page import="java.sql.*" %>

<%
int regNo = Integer.parseInt(request.getParameter("regNo"));

Class.forName("com.mysql.cj.jdbc.Driver");

Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/math",
    "root",
    "password"
);

// student info
PreparedStatement ps1 = con.prepareStatement(
    "SELECT * FROM student WHERE std_reg_no=?"
);
ps1.setInt(1, regNo);
ResultSet s = ps1.executeQuery();
s.next();
%>

<html>
<head>
<style>
body{background:#0f172a;color:white;font-family:Arial;}
.card{background:#1e293b;padding:20px;margin:20px;border-radius:10px;}
button{background:green;color:white;padding:8px;border:none;}
</style>
</head>

<body>

<h1>Welcome <%= s.getString("std_name") %></h1>
<p>Balance: ₹<%= s.getDouble("balance") %></p>

<h2>Fee Records</h2>

<%
PreparedStatement ps = con.prepareStatement(
    "SELECT * FROM fee WHERE std_reg_no=?"
);
ps.setInt(1, regNo);
ResultSet rs = ps.executeQuery();

while(rs.next()){
%>

<div class="card">
<p>Month: <%= rs.getString("month") %></p>
<p>Year: <%= rs.getInt("year") %></p>
<p>Amount: ₹<%= rs.getDouble("amount") %></p>
<p>Status: <%= rs.getString("status") %></p>

<% if(rs.getString("status").equals("PENDING")){ %>
<a href="payFee?feeId=<%= rs.getInt("fee_id") %>&amount=<%= rs.getDouble("amount") %>&regNo=<%= regNo %>">
<button>Pay Now</button>
</a>
<% } %>

</div>

<% } %>

</body>
</html>