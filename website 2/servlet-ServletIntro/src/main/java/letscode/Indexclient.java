package letscode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/indexclient/*")
public class Indexclient extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content=new String("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "   <meta charset=\"UTF-8\">\n" +
                "   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "   <title>Document</title>\n" +
                "   <link rel=\"stylesheet\" href=\"/my-app/static/style/styleClient.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "   <div class=\"wrapper\">\n" +
                "      <div class=\"container\">\n" +
                "         <div class=\"table\">\n" +
                "            <div class=\"table-wrap\">\n" +
                "               <div class=\"table-rows1 rows\">个人信息</div>\n" +
                "               <div class=\"table-rows2 rows\"><a href=\"/my-app/employee/\" target=\"_blank\">货品官理</a></div>\n" +
                "               <div class=\"table-rows3 rows\">库存管理</div>\n" +
                "               <div class=\"table-rows4 rows\">4-4</div>\n" +
                "               <div class=\"table-rows5 rows\">5-5</div>\n" +
                "               <div class=\"table-rows6 rows\">6-6</div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "      </div>\n" +
                "      <div class=\"contain\">\n" +
                "         <div class=\"header\">\n" +
                "            <div class=\"header-wrap\">\n" +
                "               <div class=\"header-menu\">\n" +
                "                  admin, 欢迎使用本系统\n" +
                "               </div>\n" +
                "               <div class=\"header-icon\">\n" +
                "                  <img class=\"icon-image\" src=\"https://cdn-icons-png.flaticon.com/512/56/56745.png\" alt=\"\">\n" +
                "                  <div class=\"icon-text\">admin</div>\n" +
                "               </div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"top\">\n" +
                "            <div class=\"top-wrap\">\n" +
                "               <div class=\"top-icon\">\n" +
                "                  <img class=\"top-icon-image\" src=\"https://cdn-icons-png.flaticon.com/512/56/56745.png\" alt=\"\">\n" +
                "               </div>\n" +
                "               <div class=\"top-info\">\n" +
                "                  <span class=\"info-head\">admin,</span>\n" +
                "                  <span class=\"info-headbottom\">ID:111111</span>\n" +
                "               </div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"menu\">\n" +
                "            <div class=\"menu-head\">\n" +
                "               <div class=\"head-wrap\">\n" +
                "                  <div class=\"head-header1\">\n" +
                "                     <span>姓名</span>\n" +
                "                     <input type=\"text\">\n" +
                "                  </div>\n" +
                "                  <div class=\"head-header2\">\n" +
                "                     <span>性别</span>\n" +
                "                     <select class=\"header2-sex\" name=\"sex\">\n" +
                "                        <option value=\"男\">男</option>\n" +
                "                        <option value=\"女\">女</option>\n" +
                "                     </select>\n" +
                "                  </div>\n" +
                "               </div>\n" +
                "               <div class=\"menu-body\">\n" +
                "                  <div class=\"body-top1\">\n" +
                "                     <div class=\"top1-wrap\">\n" +
                "                        <span>地址</span>\n" +
                "                        <input type=\"text\">\n" +
                "                     </div>\n" +
                "                  </div>\n" +
                "               </div>\n" +
                "               <div class=\"body-top2\">\n" +
                "                  <div class=\"top2-wrap\">\n" +
                "                     <span>电话号码</span>\n" +
                "                     <input type=\"text\">\n" +
                "                  </div>\n" +
                "               </div>\n" +
                "               <div class=\"menu-bottom\">\n" +
                "                  <div class=\"bottom-wrap\">\n" +
                "                     <div class=\"bottom-btn\">保存</div>\n" +
                "                     <a class=\"bottom-link\" href=\"#\">删除</a>\n" +
                "                  </div>\n" +
                "               </div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "      </div>\n" +
                "   </div>\n" +
                "\n" +
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        Map<String,String[]> params = req.getParameterMap();
        resp.getWriter().write("Method doPost:\n");
        resp.getWriter().write("URI = " + uri + "\nparams = " + params + "\n");
    }
}
