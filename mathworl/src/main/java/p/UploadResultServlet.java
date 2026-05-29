package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/UploadResultServlet")
public class UploadResultServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String reg = request.getParameter("reg");

        String[] sub = request.getParameterValues("subName");
        String[] max = request.getParameterValues("maxMarks");
        String[] obt = request.getParameterValues("obtMarks");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/math",
                    "root",
                    "Himanshu#9546"
            );

            // 🔥 STEP 1: Fetch student details
            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT std_name, dob FROM student WHERE std_reg_no=?"
            );

            ps1.setInt(1, Integer.parseInt(reg));

            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                out.println("<h2 style='color:red'>❌ Student Not Found</h2>");
                return;
            }

            String name = rs.getString("std_name");
            String dob = rs.getString("dob");

            // 🔥 STEP 2: Insert results (NO duplicate columns)
            PreparedStatement ps2 = con.prepareStatement(
                    "INSERT INTO results(std_reg_no, subject_name, max_marks, obtained_marks) VALUES(?,?,?,?)"
            );

            for (int i = 0; i < sub.length; i++) {

                ps2.setInt(1, Integer.parseInt(reg));
                ps2.setString(2, sub[i]);
                ps2.setInt(3, Integer.parseInt(max[i]));
                ps2.setInt(4, Integer.parseInt(obt[i]));

                ps2.addBatch();
            }

            ps2.executeBatch();

            // 🎨 SUCCESS UI
            out.println("<html><head><style>");

            out.println("body{margin:0;font-family:Segoe UI;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;justify-content:center;align-items:center;height:100vh;}");

            out.println(".card{background:#fff;padding:35px;border-radius:15px;width:350px;text-align:center;box-shadow:0 15px 40px rgba(0,0,0,0.2);} ");

            out.println(".icon{font-size:50px;color:green;}");

            out.println(".info{margin:10px 0;color:#333;font-size:16px;}");

            out.println(".btn{margin-top:20px;padding:12px;width:100%;border:none;border-radius:10px;background:linear-gradient(to right,#667eea,#764ba2);color:#fff;cursor:pointer;font-size:16px;}");

            out.println(".btn:hover{transform:scale(1.05);} ");

            out.println("</style></head><body>");

            out.println("<div class='card'>");

            out.println("<div class='icon'>✔</div>");
            out.println("<h2>Result Uploaded Successfully</h2>");

            out.println("<div class='info'><b>Name:</b> " + name + "</div>");
            out.println("<div class='info'><b>Reg No:</b> " + reg + "</div>");
            out.println("<div class='info'><b>DOB:</b> " + dob + "</div>");

            out.println("<button class='btn' onclick=\"window.location='uploadresult.html'\">Upload Another</button>");

            out.println("</div></body></html>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}