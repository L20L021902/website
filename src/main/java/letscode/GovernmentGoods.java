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
}
