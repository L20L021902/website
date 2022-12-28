package letscode;

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

@WebServlet(urlPatterns = "/employee/*")
public class Employee extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Check authorization
        String username = TokenChecker.authenticate(req, resp);
        if (username == null) { return; }

        String content = Helpers.getWebpage(Helpers.Webpage.Employee);
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
                getGoods(req, resp);
                break;
            case "/add":
                addGoods(req, resp);
                break;
            case "/update":
                updateGoods(req, resp);
                break;
            case "/delete":
                deleteGoods(req, resp);
                break;
            default:
                resp.sendError(404);
                break;
        }
    }

    private static void getGoods(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String json = Database.getGoods();
            if (json == null) {
                resp.sendError(500);
                return;
            }

            resp.setStatus(200);
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            out.write(json);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addGoods(HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            if (!Database.addGoods(
                    (int) json.get("goods_id"),
                    (String) json.get("name"),
                    (String) json.get("category"),
                    (int) json.get("buy_price"),
                    (int) json.get("sell_price"),
                    0,
                    "却贷",
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

    private static void updateGoods(HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            if (!Database.updateGoods(
                    (int) json.get("goods_id"),
                    (String) json.get("name"),
                    (String) json.get("category"),
                    (int) json.get("buy_price"),
                    (int) json.get("sell_price"),
                    (int) json.get("stock"),
                    (int) json.get("stock") > 0 ? "有货" : "却贷",
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

    private static void deleteGoods(HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            if (!Database.deleteGoods(
                    (int) json.get("goods_id")
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

