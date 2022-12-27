package letscode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/login/*")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content=new String("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "   <meta charset=\"UTF-8\">\n" +
                "   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "   <title>Log</title>\n" +
                "   <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n" +
                "   <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n" +
                "   <link href=\"https://fonts.googleapis.com/css2?family=Open+Sans:wght@300&display=swap\" rel=\"stylesheet\">\n" +
                "\n" +
                "   <link rel=\"stylesheet\" href=\"/my-app/static/style/style.css\">\n" +
                "\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "   <div class=\"wrapper\">\n" +
                "      <div class=\"block\">\n" +
                "         <div class=\"block-text\">\n" +
                "            <h1>通用批发零售业务管理系统</h1>\n" +
                "         </div>\n" +
                "         <div class=\"block-log\">\n" +
                "            <div class=\"log-text\">登录</div>\n" +
                "            <div class=\"log-press\">\n" +
                "               <div class=\"log-press-one\">\n" +
                "                  <input class=\"press-login place\" type=\"text\" placeholder=\"login\">\n" +
                "               </div>\n" +
                "               <div class=\"log-press-two\" >\n" +
                "                  <input class=\"press-password place\" type=\"password\" name=\"userpass\" placeholder=\"password\">\n" +
                "               </div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"block-btn\">\n" +
                "            <div class=\"btn-button\"><a href=\"/my-app/indexclient/\" target=\"_blank\">press</a></div>\n" +
                "            <a class=\"btn-link\" href=\"#\">忘记的密码</a>\n" +
                "         </div>\n" +
                "      </div>\n" +
                "   </div>\n" +
                "   <script src=\"/my-app/static/source/script.js\"></script>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        // Параметр
        String parameter = req.getParameter("parameter");

        // Старт HTTP сессии
        HttpSession session = req.getSession(true);
        session.setAttribute("parameter", parameter);

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            out.println(content);
        } finally {
            out.close();
        }
    }
}
