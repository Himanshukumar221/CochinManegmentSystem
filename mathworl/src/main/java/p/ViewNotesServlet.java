package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ViewNotesServlet")
public class ViewNotesServlet extends HttpServlet {

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
                    "SELECT id, note_name FROM notes ORDER BY id DESC"
            );

            ResultSet rs = ps.executeQuery();

            // 🔥 HTML START
            out.println("<html><head><style>");

            out.println("body{margin:0;font-family:Segoe UI;background:linear-gradient(135deg,#1e3a8a,#9333ea);color:white;}");

            out.println(".container{padding:40px;text-align:center;}");

            out.println(".title{font-size:32px;margin-bottom:25px;font-weight:bold;}");

            out.println(".grid{display:flex;flex-wrap:wrap;justify-content:center;gap:20px;}");

            out.println(".card{background:rgba(255,255,255,0.12);backdrop-filter:blur(12px);");
            out.println("width:230px;padding:20px;border-radius:15px;box-shadow:0 10px 30px rgba(0,0,0,0.3);transition:0.3s;}");

            out.println(".card:hover{transform:scale(1.07);} ");

            out.println(".name{font-size:18px;margin-bottom:15px;font-weight:600;}");

            out.println(".btn{display:inline-block;padding:10px 15px;");
            out.println("background:linear-gradient(to right,#22c55e,#16a34a);");
            out.println("color:white;text-decoration:none;border-radius:8px;font-weight:bold;transition:0.3s;}");

            out.println(".btn:hover{background:linear-gradient(to right,#16a34a,#15803d);} ");

            out.println("</style></head><body>");

            out.println("<div class='container'>");
            out.println("<div class='title'>📚 Available Notes</div>");
            out.println("<div class='grid'>");

            boolean found = false;

            while (rs.next()) {

                found = true;

                int id = rs.getInt("id");
                String name = rs.getString("note_name");

                out.println("<div class='card'>");
                out.println("<div class='name'>" + name + "</div>");
                out.println("<a class='btn' href='DownloadNotesServlet?id=" + id + "'>⬇ Download</a>");
                out.println("</div>");
            }

            if (!found) {
                out.println("<h2>No Notes Available</h2>");
            }

            out.println("</div></div>");
            out.println("</body></html>");

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}