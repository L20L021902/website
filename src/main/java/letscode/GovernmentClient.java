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

@WebServlet(urlPatterns = "/governmentClient/*")
public class GovernmentClient extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check authorization
        String username = TokenChecker.authenticate(req, resp);
        if (username == null) { return; }

        if (req.getPathInfo() != null) {
            switch (req.getPathInfo()) {
                case "/get":
                    getClients(username, req, resp);
                    break;
                default:
                    break;
            }
        }



        StringBuilder content = new StringBuilder(Helpers.getWebpage(Helpers.Webpage.GovernmentClient));
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
                addClient(username, req, resp);
                break;
            case "/update":
                updateClient(username, req, resp);
                break;
            default:
                doGet(req, resp);
                break;
        }
    }

    private static void getClients(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            String json = Database.getClients(username);
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

    private static void addClient(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            if (!Database.addClient(
                    username,
                    (int) json.get("client_id"),
                    (String) json.get("name"),
                    (String) json.get("sex"),
                    (String) json.get("address"),
                    (long) json.get("phone")
            )) {
                resp.sendError(400);
            } else {
                resp.setStatus(200);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateClient(String username, HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            if (!Database.updateClient(
                    username,
                    (int) json.get("client_id"),
                    (String) json.get("name"),
                    (String) json.get("sex"),
                    (String) json.get("address"),
                    (long) json.get("phone")
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
