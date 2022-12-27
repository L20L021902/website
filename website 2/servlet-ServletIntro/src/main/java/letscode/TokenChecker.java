package letscode;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class TokenChecker {

    public static boolean isAllowed(HttpServletRequest req) {
        for (Cookie cookie: req.getCookies()) {
            if (Objects.equals(cookie.getName(), "token") && checkToken(cookie.getValue())) {
                return true;
            }
        }

        return false;
    }

    private static boolean checkToken(String token) {
        // TODO
        return true;
    }

}
