package p;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/StudentResultServlet")
public class StudentResultServlet extends HttpServlet {

    private static final String URL = "jdbc:mysql://localhost:3306/math";
    private static final String USER = "root";
    private static final String PASS = "Himanshu#9546";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String reg = request.getParameter("reg");
        String dob = request.getParameter("dob");

        List<String[]> list = new ArrayList<>();

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            // 🔥 Student verify
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT * FROM student WHERE std_reg_no=? AND dob=?");

            ps1.setInt(1, Integer.parseInt(reg));
            ps1.setString(2, dob);

            ResultSet rs1 = ps1.executeQuery();

            if(!rs1.next()){
                response.getWriter().println("<h3>❌ Invalid Reg No or DOB</h3>");
                return;
            }

            String name = rs1.getString("std_name");
            String start = rs1.getString("start_time");
            String end = rs1.getString("end_time");

            // 🔥 Result fetch
            PreparedStatement ps2 = con.prepareStatement(
                "SELECT * FROM results WHERE std_reg_no=?");

            ps2.setInt(1, Integer.parseInt(reg));

            ResultSet rs2 = ps2.executeQuery();

            while(rs2.next()){
                list.add(new String[]{
                    rs2.getString("subject_name"),
                    rs2.getString("max_marks"),
                    rs2.getString("obtained_marks")
                });
            }

            request.setAttribute("name", name);
            request.setAttribute("reg", reg);
            request.setAttribute("start", start);
            request.setAttribute("end", end);
            request.setAttribute("data", list);

            request.getRequestDispatcher("student_result.jsp").forward(request,response);

            con.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}