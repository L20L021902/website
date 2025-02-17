package letscode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TokenChecker {

    public static String authenticate(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getCookies() == null) {
            System.out.println("No cookies were sent with the request");
            try {
                resp.sendRedirect("/login");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        for (Cookie cookie: req.getCookies()) {

            if (Objects.equals(cookie.getName(), "token")) {

                String username = checkToken(cookie.getValue());

                if (username == null) {
                    try {
                        resp.sendRedirect("/login");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                return username;
            }
        }

        try {
            resp.sendRedirect("/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String checkToken(String token) {
        if (token == null || token.isEmpty()) {
            System.out.println("Token was empty");
            return null;
        }

        String username = null;
        List<Integer> old_tokens = new ArrayList<Integer>();

        try {
            Connection c = Database.getConnectionToMain();
            PreparedStatement stmt = c.prepareStatement("SELECT ID,USERNAME,VALID_UNTIL FROM TOKENS WHERE TOKEN IS ?");

            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getLong("VALID_UNTIL") > Instant.now().getEpochSecond()) {
                    // token is valid
                    System.out.println("Valid token was presented");
                    username = rs.getString("USERNAME");
                    break;
                } else {
                    // token expired
                    System.out.println("Expired token was presented");
                    old_tokens.add(rs.getInt("ID"));
                }
            }

            stmt.close();
            c.close();

            // delete old tokens if any
            deleteToken(old_tokens);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.printf("Token presented was: %s, found username: %s%n", token, username == null ? "null" : username);

        return username;
    }

    public static void deleteToken(List<Integer> oldTokenIDs) {
        if (oldTokenIDs.isEmpty()) { return; }

        try {
            Connection c = Database.getConnectionToMain();
            PreparedStatement stmt;

            for (int tokenID: oldTokenIDs) {
                stmt = c.prepareStatement("DELETE FROM TOKENS WHERE ID is ?");

                stmt.setInt(1, tokenID);
                stmt.executeUpdate();

                stmt.close();
            }

            c.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
