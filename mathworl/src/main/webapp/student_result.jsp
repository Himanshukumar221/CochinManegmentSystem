<%@ page import="java.util.*" %>

<html>
<head>
<style>
body{
    font-family:'Segoe UI', sans-serif;
    background: linear-gradient(135deg,#667eea,#764ba2);
    margin:0;
}

/* Container */
.container{
    width:800px;
    margin:30px auto;
    background:white;
    padding:30px;
    border-radius:12px;
    box-shadow:0 15px 40px rgba(0,0,0,0.3);
}

/* Header */
.header{
    text-align:center;
    border-bottom:3px solid #444;
    padding-bottom:10px;
}

.header h1{
    margin:0;
    color:#333;
    letter-spacing:2px;
}

.header p{
    margin:5px;
    font-weight:bold;
    color:#666;
}

/* Info Box */
.info-box{
    display:flex;
    justify-content:space-between;
    background:#f4f6fb;
    padding:15px;
    margin:20px 0;
    border-radius:10px;
}

/* Table */
table{
    width:100%;
    border-collapse:collapse;
}

th{
    background:#667eea;
    color:white;
    padding:12px;
}

td{
    padding:10px;
    text-align:center;
    border-bottom:1px solid #ddd;
}

tr:hover{
    background:#f1f1f1;
}

/* Total */
.total{
    background:#eee;
    font-weight:bold;
}

/* Result Box */
.result-box{
    margin-top:20px;
    display:flex;
    justify-content:space-between;
    font-size:18px;
    font-weight:bold;
}

/* Status Colors */
.pass{ color:green; }
.fail{ color:red; }

/* Button */
.print-btn{
    text-align:center;
    margin-top:25px;
}

button{
    padding:12px 25px;
    background:linear-gradient(to right,#667eea,#764ba2);
    color:white;
    border:none;
    border-radius:8px;
    cursor:pointer;
}

button:hover{ opacity:0.9; }

/* Print */
@media print{
    body{background:white;}
    .print-btn{display:none;}
}
</style>
</head>

<body>

<div class="container">

<!-- HEADER -->
<div class="header">
    <h1>MATHS WORLS MUNGER</h1>
    <p> Student Marksheet</p>
</div>

<!-- INFO -->
<div class="info-box">
    <div>
        <p><b>Name:</b> ${name}</p>
        <p><b>Reg No:</b> ${reg}</p>
    </div>
    <div>
        <p><b>Batch:</b> ${start} - ${end}</p>
    </div>
</div>

<!-- TABLE -->
<table>
<tr>
<th>Subject</th>
<th>Max Marks</th>
<th>Obtained</th>
</tr>

<%
List<String[]> data = (List<String[]>)request.getAttribute("data");

int totalMax = 0, totalObt = 0;

for(String[] row : data){
    int max = Integer.parseInt(row[1]);
    int obt = Integer.parseInt(row[2]);

    totalMax += max;
    totalObt += obt;
%>

<tr>
<td><%=row[0]%></td>
<td><%=max%></td>
<td><%=obt%></td>
</tr>

<% } %>

<tr class="total">
<td>Total</td>
<td><%=totalMax%></td>
<td><%=totalObt%></td>
</tr>

</table>

<%
double percentage = (totalObt * 100.0) / totalMax;

// Grade
String grade;
if(percentage >= 90) grade="A+";
else if(percentage >= 75) grade="A";
else if(percentage >= 60) grade="B";
else if(percentage >= 50) grade="C";
else grade="F";

// Result
String status = (percentage >= 40) ? "PASS" : "FAIL";
%>

<!-- RESULT SUMMARY -->
<div class="result-box">
    <div>Percentage: <%= String.format("%.2f",percentage) %>%</div>
    <div>Grade: <%= grade %></div>
    <div class="<%= status.equals("PASS") ? "pass" : "fail" %>">
        Result: <%= status %>
    </div>
</div>

<!-- BUTTON -->
<div class="print-btn">
    <button onclick="window.print()">🖨 Print Marksheet</button>
</div>

</div>

</body>
</html>