package p;

import java.io.*;
import java.sql.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DeleteNoteServlet")
public class DeleteNoteServlet extends HttpServlet {

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
                    "DELETE FROM notes WHERE id=?"
            );

            ps.setInt(1, id);
            ps.executeUpdate();

            con.close();

            response.sendRedirect("ViewNotesServlet");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}