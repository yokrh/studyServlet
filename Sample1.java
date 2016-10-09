import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Sample1 extends HttpServlet {
  public void doGet (HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println(createHTML("GET"));
    out.close();
  }

  public void doPost (HttpServletRequest request,
    HttpServletResponse response)
    throws IOException, ServletException {

    response.setContentType("text/html;charset=Shift_JIS");
    PrintWriter out = response.getWriter();
    out.println(createHTML("POST"));
    out.close();
  }

  protected String createHTML (String methodType) {
    StringBuffer sb = new StringBuffer();

    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>サンプル</title>");
    sb.append("</head>");
    sb.append("<body>");

    sb.append("<p>" + methodType + "メソッドで呼び出されました" + "</p>");

    sb.append("<p><a href=\"/sample/Sample1\">リンク</a></p>");

    sb.append("<form action=\"/sample/Sample1\" method=\"get\">");
    sb.append("<input type=\"submit\" value=\"Getで送信\" />");
    sb.append("</form>");

    sb.append("<form action=\"/sample/Sample1\" method=\"post\">");
    sb.append("<input type=\"submit\" value=\"Postで送信\" />");
    sb.append("</form>");

    sb.append("</body>");
    sb.append("</html>");

    return new String(sb);
  }
}
