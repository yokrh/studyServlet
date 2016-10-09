import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ShowURI extends HttpServlet {
  public void doGet (
    HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{
    
    // getRequestURI(), getRequestURL()の直表示は
    // パラメータの文字列から悪用される脆弱性があるので、ダメ！今後利用しない方針で。
    StringBuffer sb = new StringBuffer();
    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>テスト</title>");
    sb.append("</head>");
    sb.append("<body>");
    sb.append("<p>" + "getRequestURL: "
      + new String(request.getRequestURL()) + "</p>");
    sb.append("<p>" + "getRequestURI: "
      + request.getRequestURI() + "</p>");
    sb.append("<p>" + "getServletPath: "
      + request.getServletPath() + "</p>");

    sb.append("<br/>");
    
    // enumをCollectionsクラス利用して拡張for
    // request.getParameterNames()の場合もこれでいいかも
    //（request.getParameterMap().keySet()でもいいけど）
    List<String> pns = Collections.list(getInitParameterNames());
    sb.append("<p>" + "init-param size : " + pns.size() + "</p>");
    for (String pn : pns) {
      sb.append("<p>" + "param-name: " + pn
        + " , param-value: " + getInitParameter(pn) + "</p>");
    }
    sb.append("</body>");
    sb.append("</html>");
    
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(new String(sb));
    out.close();
  }
}