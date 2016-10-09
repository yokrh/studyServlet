import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CookieTest extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
      
      String myCookieName = "visited";
      int cookieTimerVal = 10;
      
      Cookie cookies[] = request.getCookies();
      Cookie cookie = null;
      if (cookies == null) {
        String initialVal = Integer.toString(0);
        cookie = new Cookie(myCookieName, initialVal);
      } else {
        for (Cookie c : cookies) {
          if (!c.getName().equals(myCookieName)) continue;
          cookie = c;
          break;
        }
        int val = Integer.parseInt(cookie.getValue());
        int newVal = val + 1;
        cookie.setValue(Integer.toString(newVal));
      }
      // 同名で異なるパスのcookieになるのを回避するため、自分でPathを、毎回指定する
      // MaxAgeが-1のcookieになるのを回避するため、自分で最大寿命を、毎回指定する
      cookie.setPath("/sample/CookieTest");
      cookie.setMaxAge(60);
      //log("Name: " + cookie.getName() + ", Value: " + cookie.getValue());
      //log("MaxAge: " + cookie.getMaxAge() + ", Path: " + cookie.getPath().toString());
      response.addCookie(cookie);
      
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      
      out.println("<html>");
      out.println("<head>");
      out.println("<title>クッキーテスト</title>");
      out.println("</head>");
      out.println("<body>");
      if (cookie == null) {
        out.println("クッキーが見つかりません");
      } else {
        out.println("<p>" + cookieTimerVal + "</p>");
        
        out.println("<p>" + "クッキーの名前：" + cookie.getName() + "</p>");
        out.println("<p>" + "クッキーの値：" + cookie.getValue() + "</p>");
        
        out.println("<br/>");
        out.println("<a href='/sample/CookieTest'>クッキー再表示（リロード）</a>");
        
        out.println("<br/>");
        out.println("<form action='/sample/CookieTest/Redirect' method='get' >");
        out.println("<input type='hidden' name='url' value='/sample/CookieTest' />");
        out.println("<input type='submit' value='クッキーリセット By redirect フォームボタン' />");
        out.println("</form>");
      }
      out.println("</body>");
      out.println("</html>");
    }
}
