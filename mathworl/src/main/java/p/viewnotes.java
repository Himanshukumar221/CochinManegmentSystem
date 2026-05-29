package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewNotesServlet")
public class viewnotes extends HttpServlet {

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
                    "SELECT id, note_name, uploaded_at FROM notes ORDER BY id DESC"
            );

            ResultSet rs = ps.executeQuery();

            out.println("<html><head><title>All Notes</title><style>");

            out.println("body{font-family:Arial;background:linear-gradient(135deg,#0f172a,#020617);color:white;margin:0;padding:20px;}");
            out.println("h1{text-align:center;color:#38bdf8;margin-bottom:20px;}");

            out.println(".container{width:80%;margin:auto;}");

            out.println(".card{display:flex;justify-content:space-between;align-items:center;"
                    + "background:rgba(255,255,255,0.1);padding:15px;margin:10px;border-radius:12px;"
                    + "box-shadow:0 8px 20px rgba(0,0,0,0.4);}");

            out.println(".name{font-size:18px;font-weight:bold;}");

            out.println(".btn{padding:8px 15px;border:none;border-radius:8px;cursor:pointer;"
                    + "background:red;color:white;font-weight:bold;transition:0.3s;}");

            out.println(".btn:hover{transform:scale(1.05);background:darkred;}");

            out.println("</style></head><body>");

            out.println("<h1>📚 All Notes List</h1>");
            out.println("<div class='container'>");

            boolean found = false;

            while (rs.next()) {
                found = true;

                int id = rs.getInt("id");
                String name = rs.getString("note_name");

                out.println("<div class='card'>");

                out.println("<div class='name'>📄 " + name + "</div>");

                // 👉 HAR NOTE KA ALAG DELETE BUTTON
                out.println("<div>");
                out.println("<a href='DeleteNoteServlet?id=" + id + "'>");
                out.println("<button class='btn'>Delete</button>");
                out.println("</a>");
                out.println("</div>");

                out.println("</div>");
            }

            if (!found) {
                out.println("<h2 style='text-align:center;color:red;'>No Notes Found</h2>");
            }

            out.println("</div>");
            out.println("</body></html>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}