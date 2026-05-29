package p;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/Delete")
public class DeleteStudentServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/math";
    private static final String USER = "root";
    private static final String PASS = "Himanshu#9546";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String reg = request.getParameter("std_reg_no");
        String name = request.getParameter("std_name");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            PreparedStatement ps;

            // 🔥 पहले data fetch करो (delete से पहले)
            if(reg != null && !reg.isEmpty()){
                ps = con.prepareStatement("SELECT * FROM student WHERE std_reg_no=?");
                ps.setInt(1, Integer.parseInt(reg));
            } else {
                ps = con.prepareStatement("SELECT * FROM student WHERE std_name=?");
                ps.setString(1, name);
            }

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                out.println("<h2 style='color:red;text-align:center;'>❌ Student Not Found</h2>");
                return;
            }

            // 👉 data store कर लो (display के लिए)
            String sreg = rs.getString("std_reg_no");
            String sname = rs.getString("std_name");
            String sfname = rs.getString("f_name");
            String smobile = rs.getString("mobile_no");

            // 🔥 अब delete करो
            if(reg != null && !reg.isEmpty()){
                ps = con.prepareStatement("DELETE FROM student WHERE std_reg_no=?");
                ps.setInt(1, Integer.parseInt(reg));
            } else {
                ps = con.prepareStatement("DELETE FROM student WHERE std_name=?");
                ps.setString(1, name);
            }

            int result = ps.executeUpdate();

            if(result > 0){

                // 🎨 Stylish output
                out.println("<html><head><style>");
                out.println("body{font-family:Arial;background:#111;color:white;display:flex;justify-content:center;align-items:center;height:100vh;}");
                out.println(".box{background:#222;padding:30px;border-radius:15px;width:350px;text-align:center;box-shadow:0 10px 30px rgba(0,0,0,0.5);}");
                out.println("h2{color:red;}");
                out.println("p{margin:8px 0;}");
                out.println("button{margin-top:15px;padding:10px;width:100%;background:red;color:white;border:none;border-radius:8px;cursor:pointer;}");
                out.println("</style></head><body>");

                out.println("<div class='box'>");
                out.println("<h2>🗑 Student Deleted</h2>");

                out.println("<p><b>Reg No:</b> " + sreg + "</p>");
                out.println("<p><b>Name:</b> " + sname + "</p>");
                out.println("<p><b>Father:</b> " + sfname + "</p>");
                out.println("<p><b>Mobile:</b> " + smobile + "</p>");

                out.println("<button onclick=\"window.location='delete_student.html'\">Delete Another</button>");

                out.println("</div></body></html>");

            } else {
                out.println("<h3>Delete Failed</h3>");
            }

            con.close();

        } catch(Exception e){
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}