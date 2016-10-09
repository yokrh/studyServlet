import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Sample2 extends HttpServlet {

  int count = 0;

  public void doGet (HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    count++;

    StringBuffer sb = new StringBuffer();

    sb.append("<html>");
    sb.append("<head>");
    sb.append("<title>サンプル</title>");
    sb.append("</head>");
    sb.append("<body>");

    sb.append("<p>訪問人数:" + count + "</p>");

    sb.append("</body>");
    sb.append("</html>");

    out.println(new String(sb));

    out.close();
  }
}
