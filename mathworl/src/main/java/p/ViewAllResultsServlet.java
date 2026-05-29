package p;

import java.io.*;

import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewAllResultsServlet")
public class ViewAllResultsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/math",
                    "root",
                    "Himanshu#9546"
            );

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM results "
            );

            ResultSet rs = ps.executeQuery();

            out.println("<html><head><style>");

            out.println("body{font-family:Arial;background:linear-gradient(135deg,#0f172a,#020617);color:white;padding:20px;}");
            out.println("h1{text-align:center;color:#38bdf8;}");
            out.println("table{width:95%;margin:auto;border-collapse:collapse;background:rgba(255,255,255,0.08);} ");
            out.println("th,td{padding:12px;border:1px solid rgba(255,255,255,0.2);text-align:center;}");
            out.println("th{background:#1e3a8a;}");
            out.println("tr:hover{background:rgba(255,255,255,0.1);} ");

            out.println("</style></head><body>");

            out.println("<h1>📊 Student Result List</h1>");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Reg No</th>");
            out.println("<th>Name</th>");
            out.println("<th>Subject</th>");
            out.println("<th>Max Marks</th>");
            out.println("<th>Obtained</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            while (rs.next()) {

                int reg = rs.getInt("std_reg_no");

                // 🔥 STEP: student table se name fetch
                PreparedStatement ps2 = con.prepareStatement(
                        "SELECT std_name FROM student WHERE std_reg_no=?"
                );
                ps2.setInt(1, reg);

                ResultSet rs2 = ps2.executeQuery();

                String name = "";
                if (rs2.next()) {
                    name = rs2.getString("std_name");
                }

                out.println("<tr>");
                out.println("<td>" + rs.getInt("result_id") + "</td>");
                out.println("<td>" + reg + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + rs.getString("subject_name") + "</td>");
                out.println("<td>" + rs.getInt("max_marks") + "</td>");
                out.println("<td>" + rs.getInt("obtained_marks") + "</td>");
                out.println("<td>" + rs.getTimestamp("created_at") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}