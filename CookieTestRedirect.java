import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CookieTestRedirect extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {

    response.setContentType("text/html; charset=UTF-8");

    String url = "";
    String tmp = request.getParameter("url");
    //log("パラメータ[url]の値：" + tmp);

    if (tmp == null || tmp.length() == 0) {
      url = "http://www.excite.co.jp/";
    } else if (tmp.equals("/sample/CookieTest")) {
      Cookie cookies[] = request.getCookies();
      for (Cookie c : cookies) {
        if (!c.getName().equals("visited")) continue;
        c.setMaxAge(0);
        // setPath()しないとc.getPath()==nullなので、pathの異なる同名のクッキーが作られる
        c.setPath("/sample/CookieTest");
        response.addCookie(c);
      }
      url = tmp;
    } else {
      url = "http://www.google.co.jp";
    }

    response.sendRedirect(url);
  }
}
