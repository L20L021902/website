package letscode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
                getSales(req, resp);
                break;
            default:
                resp.sendError(404);
                break;
        }
    }

    private static void getSales(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String json = Database.getSales();
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
}
