package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DownloadNotesServlet")
public class DownloadNotesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/math",
                    "root",
                    "Himanshu#9546"
            );

            PreparedStatement ps = con.prepareStatement(
                    "SELECT note_name, pdf FROM notes WHERE id=?"
            );

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                String name = rs.getString("note_name");
                Blob blob = rs.getBlob("pdf");

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                        "attachment; filename="+name+".pdf");

                InputStream in = blob.getBinaryStream();
                OutputStream out = response.getOutputStream();

                byte[] buffer = new byte[4096];
                int bytesRead;

                while((bytesRead = in.read(buffer)) != -1){
                    out.write(buffer, 0, bytesRead);
                }

                in.close();
                out.close();

            } else {
                response.getWriter().println("❌ File Not Found");
            }

            con.close();

        } catch(Exception e){
            e.printStackTrace();
            response.getWriter().println("Error: "+e.getMessage());
        }
    }
}