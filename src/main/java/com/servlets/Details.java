package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Details")
public class Details extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // Get existing session
        HttpSession session = request.getSession(false);

        // Check whether user is logged in
        if (session == null ||
                session.getAttribute("loggedInEmail") == null) {

            response.sendRedirect("Index.html");
            return;
        }

        String email = (String) session.getAttribute("loggedInEmail");

        String name = "";
        String role = "";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/usermanagement",
                    "root",
                    "ahmad@632")) {

                String query =
                        "SELECT name, email, role FROM users WHERE email = ?";

                try (PreparedStatement ps = con.prepareStatement(query)) {

                    ps.setString(1, email);

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {

                            name = rs.getString("name");
                            email = rs.getString("email");
                            role = rs.getString("role");

                        } else {

                            session.invalidate();
                            response.sendRedirect("Index.html");
                            return;
                        }
                    }
                }
            }

            // HTML DESIGN

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<title>My Profile</title>");

            out.println("<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1'>");

            out.println("<style>");

            out.println("* {"
                    + "margin:0;"
                    + "padding:0;"
                    + "box-sizing:border-box;"
                    + "}");

            out.println("body {"
                    + "font-family:Arial,sans-serif;"
                    + "background:#f3f5fb;"
                    + "min-height:100vh;"
                    + "display:flex;"
                    + "align-items:center;"
                    + "justify-content:center;"
                    + "padding:20px;"
                    + "}");

            out.println(".profile-card {"
                    + "width:100%;"
                    + "max-width:450px;"
                    + "background:white;"
                    + "border-radius:18px;"
                    + "overflow:hidden;"
                    + "box-shadow:0 8px 30px rgba(0,0,0,0.10);"
                    + "}");

            out.println(".profile-header {"
                    + "background:linear-gradient(135deg,#667eea,#764ba2);"
                    + "padding:40px 20px;"
                    + "text-align:center;"
                    + "color:white;"
                    + "}");

            out.println(".avatar {"
                    + "width:90px;"
                    + "height:90px;"
                    + "background:white;"
                    + "color:#667eea;"
                    + "border-radius:50%;"
                    + "display:flex;"
                    + "align-items:center;"
                    + "justify-content:center;"
                    + "font-size:35px;"
                    + "font-weight:bold;"
                    + "margin:0 auto 15px;"
                    + "}");

            out.println(".profile-header h2 {"
                    + "font-size:25px;"
                    + "margin-bottom:8px;"
                    + "}");

            out.println(".profile-header p {"
                    + "opacity:0.9;"
                    + "}");

            out.println(".profile-body {"
                    + "padding:30px;"
                    + "}");

            out.println(".detail {"
                    + "margin-bottom:22px;"
                    + "border-bottom:1px solid #eee;"
                    + "padding-bottom:15px;"
                    + "}");

            out.println(".detail label {"
                    + "display:block;"
                    + "color:#999;"
                    + "font-size:13px;"
                    + "margin-bottom:8px;"
                    + "}");

            out.println(".detail p {"
                    + "color:#333;"
                    + "font-size:16px;"
                    + "font-weight:600;"
                    + "}");

            out.println(".badge {"
                    + "display:inline-block;"
                    + "background:#e3f8ec;"
                    + "color:#249653;"
                    + "padding:7px 16px;"
                    + "border-radius:20px;"
                    + "font-size:13px;"
                    + "}");

            out.println(".buttons {"
                    + "display:flex;"
                    + "gap:12px;"
                    + "margin-top:25px;"
                    + "}");

            out.println(".btn {"
                    + "flex:1;"
                    + "text-align:center;"
                    + "padding:12px;"
                    + "border-radius:8px;"
                    + "text-decoration:none;"
                    + "color:white;"
                    + "font-size:14px;"
                    + "}");

            out.println(".dashboard {background:#667eea;}");

            out.println(".logout {background:#e74c3c;}");

            out.println(".btn:hover {opacity:0.85;}");

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            out.println("<div class='profile-card'>");

            out.println("<div class='profile-header'>");

            String initial = name == null || name.isEmpty()
                    ? "?"
                    : name.substring(0, 1).toUpperCase();

            out.println("<div class='avatar'>"
                    + escape(initial) + "</div>");

            out.println("<h2>" + escape(name) + "</h2>");

            out.println("<p>Welcome to your profile</p>");

            out.println("</div>");

            out.println("<div class='profile-body'>");

            out.println("<div class='detail'>");
            out.println("<label>FULL NAME</label>");
            out.println("<p>" + escape(name) + "</p>");
            out.println("</div>");

            out.println("<div class='detail'>");
            out.println("<label>EMAIL ADDRESS</label>");
            out.println("<p>" + escape(email) + "</p>");
            out.println("</div>");

            out.println("<div class='detail'>");
            out.println("<label>ACCOUNT ROLE</label>");
            out.println("<span class='badge'>"
                    + escape(role) + "</span>");
            out.println("</div>");

            out.println("<div class='buttons'>");

            out.println("<a href='DashBoard' class='btn dashboard'>"
                    + "Dashboard</a>");

            out.println("<a href='Logout' class='btn logout'>"
                    + "Logout</a>");

            out.println("</div>");

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Unable to load profile details!</h2>");
        }
    }

    private String escape(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}