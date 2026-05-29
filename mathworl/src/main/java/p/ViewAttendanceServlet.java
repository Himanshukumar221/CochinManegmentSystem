package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewAttendanceServlet")
public class ViewAttendanceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String start = request.getParameter("start_time");
        String end = request.getParameter("end_time");

        PrintWriter out = response.getWriter();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/math","root","Himanshu#9546");

            // 🔥 JOIN student + attendance
            String query = "SELECT s.std_reg_no, s.std_name, a.status, a.attendance_date " +
                           "FROM student s JOIN attendance a " +
                           "ON s.std_reg_no = a.std_reg_no " +
                           "WHERE s.start_time=? AND s.end_time=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setTime(1, Time.valueOf(start + ":00"));
            ps.setTime(2, Time.valueOf(end + ":00"));

            ResultSet rs = ps.executeQuery();

            // 🎨 UI OUTPUT
            out.println("<html><body style='font-family:Arial;background:#f4f4f4;padding:20px;'>");

            out.println("<h2>Attendance List</h2>");

            out.println("<table border='1' cellpadding='10' style='background:white;border-collapse:collapse;width:80%;margin:auto;'>");

            out.println("<tr style='background:#5b86e5;color:white;'>");
            out.println("<th>Reg No</th>");
            out.println("<th>Name</th>");
            out.println("<th>Status</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            boolean found = false;

            while(rs.next()){
                found = true;

                out.println("<tr>");
                out.println("<td>"+rs.getInt("std_reg_no")+"</td>");
                out.println("<td>"+rs.getString("std_name")+"</td>");
                out.println("<td>"+rs.getString("status")+"</td>");
                out.println("<td>"+rs.getDate("attendance_date")+"</td>");
                out.println("</tr>");
            }

            if(!found){
                out.println("<tr><td colspan='4' style='text-align:center;color:red;'>No Data Found</td></tr>");
            }

            out.println("</table>");

            out.println("<br><center>");
            out.println("<button onclick=\"window.location='viewattendence.html'\" style='padding:10px;'>Back</button>");
            out.println("</center>");

            out.println("</body></html>");

            con.close();

        }catch(Exception e){
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}