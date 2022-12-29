package letscode;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/registration/*")
public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String content = Helpers.getWebpage(Helpers.Webpage.Registration);
        assert content != null;

        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.write(content);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getPathInfo() == null) {
            resp.sendError(404);
            return;
        }

        switch (req.getPathInfo()) {
            case "/register":
                register(req, resp);
                break;
            default:
                resp.sendError(404);
                break;
        }
    }

    private static void register(HttpServletRequest req, HttpServletResponse resp) {
        try {
            JSONObject json = (JSONObject) new JSONParser().parse(req.getReader());

            if (!Database.registerUser(
                    (String) json.get("username"),
                    (String) json.get("password")
            )) {
                resp.sendError(400);
            } else {
                resp.sendRedirect("/login");
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
