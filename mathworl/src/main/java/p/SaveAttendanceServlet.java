package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/SaveAttendanceServlet")
public class SaveAttendanceServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        PrintWriter out = response.getWriter();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/math","root","Himanshu#9546");

            int total = Integer.parseInt(request.getParameter("total"));

            int present = 0;
            int absent = 0;

            for(int i=0; i<total; i++){

                String reg = request.getParameter("reg"+i);
                String name = request.getParameter("name"+i);
                String status = request.getParameter("status"+i);

                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO attendance(std_reg_no,std_name,attendance_date,status) VALUES(?,?,CURDATE(),?)");

                ps.setInt(1,Integer.parseInt(reg));
                ps.setString(2,name);
                ps.setString(3,status);

                ps.executeUpdate();

                if(status.equals("Present")) present++;
                else absent++;
            }

            // 🎨 Output UI
            out.println("<html><body style='font-family:Arial;background:#111;color:white;text-align:center;padding:50px;'>");

            out.println("<h2>✅ Attendance Saved Successfully</h2>");
            out.println("<p>Total Students: "+total+"</p>");
            out.println("<p style='color:lightgreen;'>Present: "+present+"</p>");
            out.println("<p style='color:red;'>Absent: "+absent+"</p>");

            out.println("<button onclick=\"window.location='take_attendance.html'\" style='padding:10px;'>Take Again</button>");

            out.println("</body></html>");

        }catch(Exception e){
            e.printStackTrace();
            out.println("Error: "+e.getMessage());
        }
    }
}