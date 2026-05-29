package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DeleteAttendanceServlet")
public class DeleteAttendanceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String start = request.getParameter("start_date");
        String end = request.getParameter("end_date");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/math",
                    "root",
                    "Himanshu#9546"
            );

            // 🔥 DELETE QUERY
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM attendance WHERE att_date BETWEEN ? AND ?"
            );

            ps.setString(1, start);
            ps.setString(2, end);

            int rows = ps.executeUpdate();

            // 🎨 OUTPUT UI
            out.println("<html><head><style>");

            out.println("body{font-family:Arial;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;justify-content:center;align-items:center;height:100vh;color:white;}");
            out.println(".box{background:white;color:black;padding:30px;border-radius:15px;text-align:center;box-shadow:0 10px 30px rgba(0,0,0,0.3);} ");
            out.println("h2{color:green;}");

            out.println("</style></head><body>");

            out.println("<div class='box'>");

            if (rows > 0) {
                out.println("<h2>✔ Attendance Deleted Successfully</h2>");
                out.println("<p>Total Deleted Records: " + rows + "</p>");
            } else {
                out.println("<h2 style='color:red'>❌ No Records Found</h2>");
            }

            out.println("<button onclick=\"window.location='delete_attendance.html'\">Go Back</button>");

            out.println("</div></body></html>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}