package p;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/StudentLoginServlet")
public class StudentLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int regNo = Integer.parseInt(req.getParameter("regNo"));
            String dob = req.getParameter("dob");

            // ✅ Load MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ Connection
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/math",
                "root",
                "Himanshu#9546"
            );

            // ✅ Query
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM student WHERE std_reg_no=? AND DATE(dob)=?"
            );

            ps.setInt(1, regNo);
            ps.setString(2, dob);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // ✅ Session create
                HttpSession session = req.getSession();
                session.setAttribute("regNo", regNo);

                // ✅ Redirect dashboard
                res.sendRedirect("dashboard.jsp");

            } else {

                // ❌ Invalid login
                res.sendRedirect("login.html?error=invalid");
            }

            con.close();

        } catch (Exception e) {

            // ❌ DB or driver error
            res.sendRedirect("login.html?error=db");
        }
    }

    // 🔹 TEST METHOD
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.getWriter().println("StudentLoginServlet is Working ✔");
    }
}