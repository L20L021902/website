package letscode;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/infocient/*")
public class Indexclient extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Check authorization
        String username = TokenChecker.authenticate(req, resp);
        if (username == null) {
            System.out.println("Invalid token presented while getting /indexclient.html");
            return;
        }

        String content = Helpers.getWebpage(Helpers.Webpage.InfoClient);
        assert content != null;

        content = Database.fillWithUserData(username, content);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(content);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check authorization
        String username = TokenChecker.authenticate(req, resp);
        if (username == null) { return; }

        if (req.getPathInfo() == null) {
            resp.sendError(404);
            return;
        }

        switch (req.getPathInfo()) {
            case "/update":
                updateUserInfo(username, req, resp);
                break;
            default:
                doGet(req, resp);
                break;
        }
    }

    private static void updateUserInfo(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            String realname = (String) json.get("realname");
            String sex = (String) json.get("sex");
            String address = (String) json.get("address");
            long phone = (long) json.get("phone");

            if (realname == null || sex == null || address == null || phone == 0) {
                resp.sendError(400);
                return;
            }

            if (!Database.updateUserInfo(username, realname, sex, address, phone)) {
                resp.sendError(500);
                return;
            }

            resp.setStatus(200);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
