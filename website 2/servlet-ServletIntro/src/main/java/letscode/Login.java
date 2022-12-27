package letscode;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.UUID;

@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String content;
        content = Helpers.getWebpage(Helpers.Webpage.Login);

        if (content == null) {
            resp.sendError(500);
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(content);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());
            String username = (String) json.get("username");
            String password = (String) json.get("password");

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] passwordHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Checking user + pass
            if (checkPassword(username, passwordHash)) {
                String token = generateToken(username);
                if (token == null) {
                    resp.sendError(500);
                    return;
                }

                // Sending token cookie to user
                Cookie tokenCookie = new Cookie("token", token);
                resp.addCookie(tokenCookie);

                // Redirecting to Client Info page
                resp.sendRedirect("/infocient/indexclient.html");
            } else {
                // Wrong password
                resp.sendError(403);
            }

        } catch (ParseException | NoSuchAlgorithmException e) {
            resp.sendError(500);
        }

    }

    private static String generateToken(String username) {
        String token = UUID.randomUUID().toString();

        // Save token in the database
        Connection c;
        PreparedStatement stmt;
        try {
            c = Database.getConnection();

            stmt = c.prepareStatement("INSERT INTO TOKENS VALUES (?,?)");
            stmt.setString(1, username);
            stmt.setString(2, token);
            stmt.executeUpdate();

            stmt.close();
            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }

        return token;
    }

    private static boolean checkPassword(String username, byte[] passwordHash) {
        Connection c;
        PreparedStatement stmt;
        boolean userFound = false;
        try {
            c = Database.getConnection();

            stmt = c.prepareStatement("SELECT ID FROM USERS WHERE USERNAME = ? AND PASSWORD = ?");
            stmt.setString(1, username);
            stmt.setBlob(2, new ByteArrayInputStream(passwordHash));
            ResultSet rs  = stmt.executeQuery();

            userFound = rs.next(); // if the query returned something than user+pass combo exists

            stmt.close();
            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }

        return userFound;
    }
}
