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

        if (req.getPathInfo() != null) {
            switch (req.getPathInfo()) {
                case "/get":
                    getGoods(username, req, resp);
                    return;
                case "/delete":
                    deleteGoods(username, req, resp);
                    return;
                default:
                    break;
            }
        }

        StringBuilder content = new StringBuilder(Helpers.getWebpage(Helpers.Webpage.Employee));
        assert content != null;

        Helpers.replaceOnce(content, "$(username)", username);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(content.toString());
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
            case "/add":
                addGoods(username, req, resp);
                break;
            case "/update":
                updateGoods(username, req, resp);
                break;
            default:
                doGet(req, resp);
                break;
        }
    }

    private static void getGoods(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String json = Database.getGoods(username);
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

    private static void addGoods(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String jsonString = Helpers.readFromInputStream(req.getInputStream());

            if (jsonString.isEmpty()) {
                resp.sendError(400);
                return;
            }

            JSONObject json = (JSONObject) new JSONParser().parse(jsonString);

            if (!Database.addGoods(
                    username,
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

    private static void updateGoods(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            if (!Database.updateGoods(
                    username,
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

    private static void deleteGoods(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String goods_id = req.getParameter("goods_id");

            if (goods_id == null) {
                resp.sendError(400);
                return;
            }

            if (!Database.deleteGoods(
                    username,
                    Integer.parseInt(goods_id)
            )) {
                resp.sendError(400);
            } else {
                // success
                resp.sendRedirect("/employee");
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                resp.sendError(500);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (NumberFormatException e) {
            try {
                resp.sendError(400);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}

