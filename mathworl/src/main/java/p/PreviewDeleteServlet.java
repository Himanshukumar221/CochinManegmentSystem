package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/PreviewDeleteServlet")
public class PreviewDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String start = request.getParameter("start_id");
        String end = request.getParameter("end_id");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/math",
                    "root",
                    "Himanshu#9546"
            );

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM results WHERE result_id BETWEEN ? AND ?"
            );

            ps.setInt(1, Integer.parseInt(start));
            ps.setInt(2, Integer.parseInt(end));

            ResultSet rs = ps.executeQuery();

            out.println("<html><body style='font-family:Arial;text-align:center;'>");
            out.println("<h2>Preview Results Before Delete</h2>");

            out.println("<table border='1' style='margin:auto;width:80%'>");
            out.println("<tr><th>ID</th><th>Reg No</th><th>Subject</th><th>Marks</th></tr>");

            boolean found = false;

            while(rs.next()){
                found = true;

                out.println("<tr>");
                out.println("<td>"+rs.getInt("result_id")+"</td>");
                out.println("<td>"+rs.getInt("std_reg_no")+"</td>");
                out.println("<td>"+rs.getString("subject_name")+"</td>");
                out.println("<td>"+rs.getInt("obtained_marks")+"</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if(found){

                out.println("<form action='DeleteFinalServlet' method='post'>");
                out.println("<input type='hidden' name='start_id' value='"+start+"'>");
                out.println("<input type='hidden' name='end_id' value='"+end+"'>");
                out.println("<br><button style='padding:10px;background:red;color:white;'>DELETE NOW</button>");
                out.println("</form>");
            }

            out.println("</body></html>");

            con.close();

        } catch(Exception e){
            e.printStackTrace();
            out.println("Error: "+e.getMessage());
        }
    }
}