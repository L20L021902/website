package letscode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/stocks/*")
public class Stocks extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check authorization
        String username = TokenChecker.authenticate(req, resp);
        if (username == null) { return; }

        if (req.getPathInfo() != null) {
            switch (req.getPathInfo()) {
                case "/get":
                    getGoods(username, req, resp);
                    return;
                default:
                    break;
            }
        }

        StringBuilder content = new StringBuilder(Helpers.getWebpage(Helpers.Webpage.Stocks));
        assert content != null;

        Helpers.replaceOnce(content, "$(username)", username);

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(content.toString());
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
}
