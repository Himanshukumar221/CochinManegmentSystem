package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/LoadStudentServlet")
public class LoadStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String start = request.getParameter("start_time");
        String end = request.getParameter("end_time");

        PrintWriter out = response.getWriter();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/math","root","Himanshu#9546");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM student WHERE start_time=? AND end_time=?");

            ps.setTime(1, Time.valueOf(start + ":00"));
            ps.setTime(2, Time.valueOf(end + ":00"));

            ResultSet rs = ps.executeQuery();

            out.println("<html><body style='font-family:Arial;background:#f4f4f4;padding:20px;'>");
            out.println("<h2>Mark Attendance</h2>");

            out.println("<form action='SaveAttendanceServlet' method='post'>");

            int i = 0;

            while(rs.next()){
                int reg = rs.getInt("std_reg_no");
                String name = rs.getString("std_name");

                out.println("<div style='background:white;padding:10px;margin:10px;border-radius:10px;'>");

                out.println("<b>"+reg+" - "+name+"</b><br>");

                out.println("<input type='hidden' name='reg"+i+"' value='"+reg+"'>");
                out.println("<input type='hidden' name='name"+i+"' value='"+name+"'>");

                out.println("<input type='radio' name='status"+i+"' value='Present' checked> Present ");
                out.println("<input type='radio' name='status"+i+"' value='Absent'> Absent");

                out.println("</div>");

                i++;
            }

            out.println("<input type='hidden' name='total' value='"+i+"'>");

            out.println("<button style='padding:10px;background:green;color:white;border:none;'>Save Attendance</button>");

            out.println("</form>");
            out.println("</body></html>");

        }catch(Exception e){
            e.printStackTrace();
            out.println("Error: "+e.getMessage());
        }
    }
}