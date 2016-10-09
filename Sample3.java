import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Sample3 extends HttpServlet {

  private int count;

  public void init() throws ServletException{
    count = 100;
    log("カウント開始します");
  }

  public void destroy(){
    log("カウントの最終値は" + count + "でした");
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException{

    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    StringBuffer sb = new StringBuffer();

    count++;

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
