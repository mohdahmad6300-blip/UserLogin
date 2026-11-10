package com.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/Admins")
public class Admins extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        int totalAdmins = 0;

        String dbURL = "jdbc:mysql://localhost:3306/usermanagement";
        String dbUser = "root";
        String dbPassword = "ahmad@632";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(
                    dbURL, dbUser, dbPassword)) {

                // Count total admins
                String countQuery =
                        "SELECT COUNT(*) FROM users WHERE role = ?";

                try (PreparedStatement ps = con.prepareStatement(countQuery)) {

                    ps.setString(1, "ADMIN");

                    try (ResultSet rs = ps.executeQuery()) {

                        if (rs.next()) {
                            totalAdmins = rs.getInt(1);
                        }
                    }
                }

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>All Admins</title>");

                out.println("<style>");

                out.println("* {box-sizing:border-box;margin:0;padding:0;}");

                out.println("body {"
                        + "font-family:Arial,sans-serif;"
                        + "background:#f3f5fb;"
                        + "padding:35px;"
                        + "}");

                out.println(".container {"
                        + "max-width:1100px;"
                        + "margin:auto;"
                        + "background:white;"
                        + "padding:30px;"
                        + "border-radius:15px;"
                        + "box-shadow:0 5px 25px rgba(0,0,0,0.08);"
                        + "}");

                out.println("h1 {color:#252b48;font-size:28px;}");

                out.println(".subtitle {color:#888;margin-top:8px;}");

                out.println(".top {"
                        + "display:flex;"
                        + "justify-content:space-between;"
                        + "align-items:center;"
                        + "flex-wrap:wrap;"
                        + "gap:15px;"
                        + "margin-bottom:25px;"
                        + "}");

                out.println(".count {"
                        + "background:#fff0e8;"
                        + "color:#e07837;"
                        + "padding:20px;"
                        + "border-radius:10px;"
                        + "margin-bottom:25px;"
                        + "font-size:18px;"
                        + "font-weight:bold;"
                        + "}");

                out.println("input {"
                        + "padding:12px;"
                        + "border:1px solid #ddd;"
                        + "border-radius:8px;"
                        + "outline:none;"
                        + "width:250px;"
                        + "}");

                out.println("input:focus {border-color:#667eea;}");

                out.println(".table-wrapper {overflow-x:auto;}");

                out.println("table {"
                        + "width:100%;"
                        + "border-collapse:collapse;"
                        + "min-width:550px;"
                        + "}");

                out.println("th {"
                        + "background:#667eea;"
                        + "color:white;"
                        + "padding:16px;"
                        + "text-align:left;"
                        + "}");

                out.println("td {"
                        + "padding:16px;"
                        + "border-bottom:1px solid #eee;"
                        + "}");

                out.println("tbody tr:hover {background:#f7f8ff;}");

                out.println(".badge {"
                        + "background:#fff0d9;"
                        + "color:#d18a19;"
                        + "padding:7px 14px;"
                        + "border-radius:20px;"
                        + "font-size:12px;"
                        + "font-weight:bold;"
                        + "}");

                out.println(".avatar {"
                        + "display:inline-flex;"
                        + "width:35px;"
                        + "height:35px;"
                        + "border-radius:50%;"
                        + "background:#e07837;"
                        + "color:white;"
                        + "align-items:center;"
                        + "justify-content:center;"
                        + "font-weight:bold;"
                        + "margin-right:10px;"
                        + "}");

                out.println(".back {"
                        + "display:inline-block;"
                        + "margin-top:25px;"
                        + "background:#667eea;"
                        + "color:white;"
                        + "padding:12px 20px;"
                        + "text-decoration:none;"
                        + "border-radius:7px;"
                        + "}");

                out.println(".back:hover {background:#4c5bc4;}");

                out.println("@media(max-width:600px) {"
                        + "body {padding:15px;}"
                        + ".container {padding:18px;}"
                        + "input {width:100%;}"
                        + "}");

                out.println("</style>");
                out.println("</head>");

                out.println("<body>");

                out.println("<div class='container'>");

                out.println("<div class='top'>");

                out.println("<div>");
                out.println("<h1>All Administrators</h1>");
                out.println("<p class='subtitle'>Manage and view all registered admins</p>");
                out.println("</div>");

                out.println("<input type='text' id='searchInput' "
                        + "placeholder='Search admins...'>");

                out.println("</div>");

                // Total admin count
                out.println("<div class='count'>"
                        + "Total Registered Admins: " + totalAdmins
                        + "</div>");

                out.println("<div class='table-wrapper'>");

                out.println("<table id='adminTable'>");

                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>SN</th>");
                out.println("<th>NAME</th>");
                out.println("<th>EMAIL ADDRESS</th>");
                out.println("<th>ROLE</th>");
                out.println("</tr>");
                out.println("</thead>");

                out.println("<tbody>");

                // Fetch only ADMIN records
                String adminQuery =
                        "SELECT name, email, role FROM users WHERE role = ?";

                try (PreparedStatement ps = con.prepareStatement(adminQuery)) {

                    ps.setString(1, "ADMIN");

                    try (ResultSet rs = ps.executeQuery()) {

                        int i = 1;

                        while (rs.next()) {

                            String name = escape(rs.getString("name"));
                            String email = escape(rs.getString("email"));
                            String role = escape(rs.getString("role"));

                            String initial = name.isEmpty()
                                    ? "?"
                                    : name.substring(0, 1).toUpperCase();

                            out.println("<tr>");

                            out.println("<td>" + i + "</td>");

                            out.println("<td>"
                                    + "<span class='avatar'>" + initial + "</span>"
                                    + name
                                    + "</td>");

                            out.println("<td>" + email + "</td>");

                            out.println("<td><span class='badge'>"
                                    + role + "</span></td>");

                            out.println("</tr>");

                            i++;
                        }

                        if (i == 1) {
                            out.println("<tr><td colspan='4' "
                                    + "style='text-align:center;'>"
                                    + "No Admins Found!"
                                    + "</td></tr>");
                        }
                    }
                }

                out.println("</tbody>");
                out.println("</table>");

                out.println("</div>");

                out.println("<a href='http://localhost:8085/UserLoginJDBC/DashBoard' class='back'>"
                        + "&#8592; Back to Dashboard</a>");

                out.println("</div>");

                // Search functionality
                out.println("<script>");

                out.println("document.getElementById('searchInput')"
                        + ".addEventListener('keyup', function() {");

                out.println("let value = this.value.toLowerCase();");

                out.println("let rows = document.querySelectorAll("
                        + "'#adminTable tbody tr');");

                out.println("rows.forEach(function(row) {");

                out.println("row.style.display = "
                        + "row.innerText.toLowerCase().includes(value) ? '' : 'none';");

                out.println("});");
                out.println("});");

                out.println("</script>");

                out.println("</body>");
                out.println("</html>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Something went wrong while loading admins!</h2>");
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