package p;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/payFee")
public class PaymentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        int feeId = Integer.parseInt(req.getParameter("feeId"));
        int regNo = Integer.parseInt(req.getParameter("regNo"));
        double amount = Double.parseDouble(req.getParameter("amount"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/math",
                "root",
                "password"
            );

            // 1️⃣ fee status update
            PreparedStatement ps1 = con.prepareStatement(
                "UPDATE fee SET status='PAID' WHERE fee_id=?"
            );
            ps1.setInt(1, feeId);
            ps1.executeUpdate();

            // 2️⃣ student balance कम करो
            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE student SET balance = balance - ? WHERE std_reg_no=?"
            );
            ps2.setDouble(1, amount);
            ps2.setInt(2, regNo);
            ps2.executeUpdate();

            // 3️⃣ payment table insert
            PreparedStatement ps3 = con.prepareStatement(
                "INSERT INTO payment(std_reg_no, fee_id, razorpay_payment_id, amount, status) VALUES(?,?,?,?,?)"
            );

            ps3.setInt(1, regNo);
            ps3.setInt(2, feeId);
            ps3.setString(3, "TEST123"); // later Razorpay ID
            ps3.setDouble(4, amount);
            ps3.setString(5, "SUCCESS");

            ps3.executeUpdate();

            res.sendRedirect("dashboard.jsp?regNo=" + regNo);

        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}