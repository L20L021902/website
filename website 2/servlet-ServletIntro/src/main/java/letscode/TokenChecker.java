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
import java.util.Objects;

public class TokenChecker {

    public static boolean authorize(HttpServletRequest req, HttpServletResponse resp) {
        for (Cookie cookie: req.getCookies()) {

            if (Objects.equals(cookie.getName(), "token")) {

                switch (checkToken(cookie.getValue())) {
                    case Valid:
                        return true;
                    case Invalid:
                        try {
                            resp.sendRedirect("/login");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;

                }

            }

        }

        return false;
    }

    enum TOKEN_STATUS {
        Valid,
        Invalid
    }
    private static TOKEN_STATUS checkToken(String token) {
        if (token == null || token.isEmpty()) {
            return TOKEN_STATUS.Invalid;
        }

        TOKEN_STATUS tokenStatus = TOKEN_STATUS.Invalid;

        try {
            Connection c = Database.getConnection();
            PreparedStatement stmt = c.prepareStatement("SELECT ID,VALID_UNTIL FROM TOKENS WHERE TOKEN IS ?");

            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getLong("VALID_UNTIL") > Instant.now().getEpochSecond()) {
                    // token is valid
                    tokenStatus = TOKEN_STATUS.Valid;
                    break;
                } else {
                    // token expired
                    deleteToken(rs.getInt("ID"));
                }
            }

            stmt.close();
            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return tokenStatus;
    }

    private static void deleteToken(int tokenID) {
        try {
            Connection c = Database.getConnection();
            PreparedStatement stmt = c.prepareStatement("DELETE FROM TOKENS WHERE ID is ?");

            stmt.setInt(1, tokenID);
            stmt.executeUpdate();

            stmt.close();
            c.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
