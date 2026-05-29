package p;

import java.io.*;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/UploadNotesServlet")
@MultipartConfig
public class UploadNotesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String name = request.getParameter("note_name");
        Part filePart = request.getPart("pdf");

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/math",
                    "root",
                    "Himanshu#9546"
            );

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO notes(note_name, pdf) VALUES(?,?)"
            );

            ps.setString(1, name);
            ps.setBlob(2, filePart.getInputStream());

            ps.executeUpdate();

            response.getWriter().println("✔ Notes Uploaded Successfully");

            con.close();

        } catch(Exception e){
            e.printStackTrace();
            response.getWriter().println("Error: "+e.getMessage());
        }
    }
}