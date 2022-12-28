package letscode;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/style/*", "/source/*", "/image/*"})
public class Static extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        byte[] content;
        content = Helpers.getResource("html" + req.getRequestURI());

        if (content == null) {
            resp.sendError(404);
            return;
        }

        try (ServletOutputStream out = resp.getOutputStream()) {
            out.write(content);
        }
    }
}
