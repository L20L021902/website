package letscode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

@WebServlet(urlPatterns = "/governmentGoods/*")
public class GovernmentGoods extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check authorization
        String username = TokenChecker.authenticate(req, resp);
        if (username == null) { return; }

        String content = Helpers.getWebpage(Helpers.Webpage.GovernmentGoods);
        assert content != null;

        // TODO replace placeholders

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
            case "/get":
                getSales(username, req, resp);
                break;
            default:
                doGet(req, resp);
                break;
        }
    }

    private static void getSales(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String json = Database.getSales(username);
            if (json == null) {
                resp.sendError(500);
                return;
            }

            resp.setStatus(200);
            resp.setContentType("application/json");
            try (PrintWriter out = resp.getWriter()) {
                out.write(json);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addSale(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonString = Helpers.readFromInputStream(req.getInputStream());

            if (jsonString.isEmpty()) {
                resp.sendError(400);
                return;
            }

            JSONObject json = (JSONObject) new JSONParser().parse(jsonString);
            JSONArray orderContents = (JSONArray) json.get("order_goods");

            if (!Database.addSale(
                    username,
                    (int) json.get("order_id"),
                    (int) json.get("client_id"),
                    orderContents,
                    Instant.now().getEpochSecond()
            )) {
                resp.sendError(400);
            } else {
                resp.setStatus(200);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
