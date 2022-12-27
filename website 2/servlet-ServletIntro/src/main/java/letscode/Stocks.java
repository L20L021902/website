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
        String content=new String("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "   <meta charset=\"UTF-8\">\n" +
                "   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "   <title>Document</title>\n" +
                "   <link rel=\"stylesheet\" href=\"/my-app/static/style/stocks.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "   <div class=\"wrapper\">\n" +
                "      <div class=\"container\">\n" +
                "         <div class=\"table\">\n" +
                "            <div class=\"table-wrap\">\n" +
                "               <div class=\"table-rows1 rows\"><a href=\"/my-app/indexclient/\" target=\"_blank\">个人信息</a></div>\n" +
                "               <div class=\"table-rows2 rows\"><a href=\"/my-app/employee/\">货品官理</a></div>\n" +
                "               <div class=\"table-rows3 rows\"><a href=\"/my-app/stocks/\" target=\"_blank\">库存管理</a></div>\n" +
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
                "                  库存管理\n" +
                "               </div>\n" +
                "               <div class=\"header-icon\">\n" +
                "                  <img class=\"icon-image\" src=\"https://cdn-icons-png.flaticon.com/512/56/56745.png\" alt=\"\">\n" +
                "                  <div class=\"icon-text\">admin</div>\n" +
                "               </div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"hat\">\n" +
                "            <div class=\"hat-wrap\">\n" +
                "               <div class=\"hat-textl\">库存查询</div>\n" +
                "               <div class=\"hat-text2\">库存盘点</div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"head\">\n" +
                "            <div class=\"head-wrap\">\n" +
                "               <div class=\"head-look\">\n" +
                "                  <span class=\"look-text\">货品编号：</span>\n" +
                "                  <input type=\"text\" class=\"look-input\" placeholder=\"输入编号\">\n" +
                "               </div>\n" +
                "               <div class=\"head-btn\">查询</div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"search\">\n" +
                "            <div class=\"search-wrap\">\n" +
                "               <div class=\"search-one\">\n" +
                "                  <span class=\"one-string string\">货品名称：</span>\n" +
                "                  <input type=\"text\" class=\"one-input inpt\">\n" +
                "               </div>\n" +
                "               <div class=\"searach-two\">\n" +
                "                  <span class=\"two-string string\">商品分类：</span>\n" +
                "                  <input type=\"text\" class=\"two-input inpt\">\n" +
                "               </div>\n" +
                "               <div class=\"search-three\">\n" +
                "                  <span class=\"three-string string\">商品成本：</span>\n" +
                "                  <input type=\"text\" class=\"three-input inpt\">\n" +
                "               </div>\n" +
                "               <div class=\"search-four\">\n" +
                "                  <span class=\"four-string string\">商品性价：</span>\n" +
                "                  <input type=\"text\" class=\"four-input inpt\">\n" +
                "               </div>\n" +
                "               <div class=\"search-five\">\n" +
                "                  <span class=\"five-string sting\">库存数量：</span>\n" +
                "                  <input type=\"text\" class=\"five-input inpt\">\n" +
                "               </div>\n" +
                "               <div class=\"search-btn\">确认</div>\n" +
                "               <a href=\"\" class=\"search-null\">取消</a>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"position\">\n" +
                "            <div class=\"position-wrap\">\n" +
                "               <div class=\"position-text\">库存商品总计:nnn</div>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "         <div class=\"list\">\n" +
                "            <div class=\"list-wrap\">\n" +
                "               <table class=\"list-table\">\n" +
                "                  <thead>\n" +
                "                     <tr class=\"table-cells\">\n" +
                "                        <th>序号</th>\n" +
                "                        <th>货品编号</th>\n" +
                "                        <th>货品名称</th>\n" +
                "                        <th>商品分类</th>\n" +
                "                        <th>商品成本</th>\n" +
                "                        <th>商品数量</th>\n" +
                "                        <th>状态</th>\n" +
                "                        <th>更新时间</th>\n" +
                "                        <th>库存盘点</th>\n" +
                "                        <th>销售出库</th>\n" +
                "                     </tr>\n" +
                "                  </thead>\n" +
                "                  <tbody>\n" +
                "                     <tr class=\"table-rows\">\n" +
                "                        <td>1</td>\n" +
                "                        <td>567001231</td>\n" +
                "                        <td>Apple</td>\n" +
                "                        <td>电子产品</td>\n" +
                "                        <td>5999</td>\n" +
                "                        <td>0</td>\n" +
                "                        <td class=\"cells-status1\">却贷</td>\n" +
                "                        <td>2022-10-23 16:15:00</td>\n" +
                "                        <td class=\"cells-btn\">\n" +
                "                           <a href=\"\" class=\"cells-btn1\">库存盘点</a>\n" +
                "                           <a href=\"\" class=\"cells-btn2\">销售出库</a>\n" +
                "                        </td>\n" +
                "                     </tr>\n" +
                "                     <tr>\n" +
                "                        <td>2</td>\n" +
                "                        <td>456782345</td>\n" +
                "                        <td>圆规</td>\n" +
                "                        <td>文化用品</td>\n" +
                "                        <td>3</td>\n" +
                "                        <td>100</td>\n" +
                "                        <td class=\"cells-status2\">有货</td>\n" +
                "                        <td>2022-10-24 17:15:00</td>\n" +
                "                        <td class=\"cells-btn\">\n" +
                "                           <a href=\"\" class=\"cells-btn1\">库存盘点</a>\n" +
                "                           <a href=\"\" class=\"cells-btn2\">销售出库</a>\n" +
                "                        </td>\n" +
                "                     </tr>\n" +
                "                     <tr>\n" +
                "                        <td>3</td>\n" +
                "                        <td>667512945</td>\n" +
                "                        <td>布熊</td>\n" +
                "                        <td>玩具</td>\n" +
                "                        <td>50</td>\n" +
                "                        <td>150</td>\n" +
                "                        <td class=\"cells-status2\">有贷</td>\n" +
                "                        <td>2022-10-24 17:18:00</td>\n" +
                "                        <td class=\"cells-btn\">\n" +
                "                           <a href=\"\" class=\"cells-btn1\">库存盘点</a>\n" +
                "                           <a href=\"\" class=\"cells-btn2\">销售出库</a>\n" +
                "                        </td>\n" +
                "                     </tr>\n" +
                "                  </tbody>\n" +
                "               </table>\n" +
                "            </div>\n" +
                "         </div>\n" +
                "      </div>\n" +
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
