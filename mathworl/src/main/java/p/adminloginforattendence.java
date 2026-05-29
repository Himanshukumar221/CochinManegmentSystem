package p;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Admin")
public class adminloginforattendence extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user = request.getParameter("userid");
        String pass = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if(user.equals("MATHSWORLD") && pass.equals("Akshay#98")) {

            // ✅ Success + Redirect
            out.println("<html><body style='background:black;color:white;text-align:center;'>");
            out.println("<h1>✅ Login Successfully</h1>");
            
            // JavaScript redirect after 2 seconds
            out.println("<script>");
            out.println("setTimeout(function(){");
            out.println("window.location='take_attendance.html';");
            out.println("}, 2000);");
            out.println("</script>");

            out.println("</body></html>");

        } else {

            out.println("<html><body style='background:black;color:white;text-align:center;'>");
            out.println("<h2>❌ Invalid User ID or Password</h2>");
            out.println("<a href='admin_login.html' style='color:orange;'>Try Again</a>");
            out.println("</body></html>");
        }
    }
}