package p;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/AddStudentServlet")
public class AddStudentServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/math";
    private static final String USER = "root";
    private static final String PASS = "Himanshu#9546";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reg = request.getParameter("std_reg_no");
        String name = request.getParameter("std_name");
        String fname = request.getParameter("f_name");
        String mobile = request.getParameter("mobile_no");
        String dob = request.getParameter("dob");
        String address = request.getParameter("std_address");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String query = "INSERT INTO student (std_reg_no, std_name, f_name, mobile_no, dob, std_address, start_time, end_time) VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, Integer.parseInt(reg));
            ps.setString(2, name);
            ps.setString(3, fname);
            ps.setString(4, mobile);
            ps.setString(5, dob);
            ps.setString(6, address);
            ps.setTime(7, Time.valueOf(startTime + ":00"));
            ps.setTime(8, Time.valueOf(endTime + ":00"));

            int result = ps.executeUpdate();

            if(result > 0){

                // 🔥 Stylish Output
                out.println("<html><head><style>");
                out.println("body{font-family:Arial;background:linear-gradient(135deg,#667eea,#764ba2);display:flex;justify-content:center;align-items:center;height:100vh;}");
                out.println(".card{background:white;padding:30px;border-radius:15px;width:350px;box-shadow:0 15px 35px rgba(0,0,0,0.2);}");
                out.println("h2{text-align:center;color:green;}");
                out.println("p{margin:8px 0;font-size:15px;}");
                out.println("button{margin-top:15px;padding:10px;width:100%;background:#667eea;color:white;border:none;border-radius:8px;cursor:pointer;}");
                out.println("</style></head><body>");

                out.println("<div class='card'>");
                out.println("<h2>✅ Student Added</h2>");

                out.println("<p><b>Reg No:</b> " + reg + "</p>");
                out.println("<p><b>Name:</b> " + name + "</p>");
                out.println("<p><b>Father:</b> " + fname + "</p>");
                out.println("<p><b>Mobile:</b> " + mobile + "</p>");
                out.println("<p><b>DOB:</b> " + dob + "</p>");
                out.println("<p><b>Address:</b> " + address + "</p>");
                out.println("<p><b>Timing:</b> " + startTime + " to " + endTime + "</p>");

                out.println("<button onclick=\"window.location='studentadd.html'\">Add Another</button>");

                out.println("</div></body></html>");

            } else {
                out.println("<h3>Failed to Insert</h3>");
            }

            con.close();

        } catch(Exception e){
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}