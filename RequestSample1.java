import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class RequestSample1 extends HttpServlet {
  public void doPost (HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    // 当たり前だが、
    // 一度response書いてしまうと、doGetのresponseが返されなくなる
    // 一部を共通の処理にする場合は、メソッド切り出すべき
    // 全く同じなら、doGet()呼ぶだけでよい
    /*
    //response.setContentType("text/html; charset=UTF-8");
    //PrintWriter out = response.getWriter();
    //out.println("<h1>doPost</h1>");
    //out.close();
    */

    doGet(request, response);
  }

  public void doGet (HttpServletRequest request,
    HttpServletResponse response) throws IOException, ServletException {

    // string
    String name = request.getParameter("name");
    if (name == null || name.length() == 0) {
      name = "";
    }

    // int
    String oldVal = request.getParameter("old");
    int old = -2;
    if (oldVal == null || oldVal.length() == 0) {
      old = -1;
    } else {
      try {
        old = Integer.parseInt(oldVal);
      } catch (NumberFormatException e) {
        old = -1;
      }
    }

    // array
    String foodVals[] = request.getParameterValues("food");
    String foods = "";
    if (foodVals == null) {
      foods = "";
    } else {
      for (int i=0;i<foodVals.length;i++) {
        foods += foodVals[i];
        if (i != foodVals.length-1) foods += " ";
      }
    }

    StringBuffer sb = new StringBuffer();
    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>サンプル</title>");
    sb.append("</head>");
    sb.append("<body>");
    
    // getParameterNames
    //Enumeration params = request.getParameterNames();
    // こっちのほうがいい
    //Set<String> parameterNames = request.getParameterMap().keySet();
    // こっちのほうがもっといい。getInitParameterNames()との整合性的に
    List<String> parameterNames = Collections.list(request.getParameterNames());
    sb.append("parameterNames.size(): " + parameterNames.size());
    for (String pn : parameterNames) sb.append("<p>" + pn + "</p>");
    
    sb.append("<p>お名前は " + (name == "" ? "選択されていません" : name) + " です</p>");
    sb.append("<p>年齢は " + (old == -1 ? "未設定" : old) + " です</p>");
    sb.append("<p>好きな果物は " + (foods == "" ? "選択されていません" : foods) + " です</p>");
    sb.append("</body>");
    sb.append("</html>");

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<h1>" + request.getMethod() + "</h1>");
    out.println(new String(sb));
    out.close();
  }
}