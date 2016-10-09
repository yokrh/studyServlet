import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class SessionTest extends HttpServlet {
  public void doGet(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession(false);

    out.println("<html>");
    out.println("<head>");
    out.println("<title>セッションテスト</title>");
    out.println("</head>");
    out.println("<body>");

    if (session == null) {
      // セッション開始、セッションオブジェクト登録
      out.println("<p>セッションなし。開始します</p>");
      out.println("初回訪問です");

      out.println("<p>セッションオブジェクトを登録します</p>");
      session = request.getSession(true);

      session.setAttribute("visited", "1");
      session.setAttribute("name", "YAMADA");
      session.setAttribute("age", "18");
    } else {
      // 訪問回数+1
      String visitedStr = (String)session.getAttribute("visited");
      int visited = Integer.parseInt(visitedStr);
      visited++;

      out.println("<p>セッションあり</p>");
      out.println("<p>訪問回数は" + visited + "回目です</p>");

      session.setAttribute("visited", Integer.toString(visited));
    }

    // セッション情報の表示
    String session_id = session.getId();
    long createTime = session.getCreationTime();
    Date createDate = new Date(createTime);
    long accessTime = session.getLastAccessedTime();
    Date accessDate = new Date(accessTime);
    int intervalTime = session.getMaxInactiveInterval();
    out.println("<br/>");
    out.println("<p>");
    out.println("セッションIDは" + session_id + "です（2回目以降はcookieに含まれている）" + "<br/>");
    out.println("<br/>");
    out.println("セッション開始時間:" + createDate + "<br/>");
    out.println("最終アクセス時間  :" + accessDate + "<br/>");
    out.println("デフォルトの有効期限は" + intervalTime + "秒です" + "<br/>");
    out.println("</p>");

    // 全attribute表示
    out.println("<br/>");
    out.println("<p>[ show all attribute 1 ]</p>");
    showSessionAttrs(out, session);

    if (session.getAttribute("age") == null) {
      session.setMaxInactiveInterval(10);

      if (Integer.parseInt((String)session.getAttribute("visited")) >= 5) {
        // セッション終了
        session.invalidate();

        out.println("<br/>");
        out.println("<p>セッション終了します</p>");
      }
    } else {
      // Session[age]削除し、全attribute表示
      session.removeAttribute("age");
      out.println("<br/>");
      out.println("<p>Session[age] deleted</p>");

      out.println("<br/>");
      out.println("<p>[ show all attribute 2 ]</p>");
      showSessionAttrs(out, session);
    }

    // cookie無効のクライアントに対して、
    // urlに自動にsessionIDを持たせて擬似セッションを実現できる
    // （有効ならば何もしない。つまり、ブラウザのcookie無効で書き換えあり）
    String url = "/sample/SessionTest";
    String eURL = response.encodeURL(url);
    out.println("<br/>");
    out.println("<p>");
    out.println("response.encodeURL(url)" + "<br/>");
    out.println("書き換え前: " + url + "<br/>");
    out.println("書き換え後: " + eURL);
    out.println("</p>");
    // cookie経由かURL書き換えによるものか、判別
    boolean cookie_flag = request.isRequestedSessionIdFromCookie();
    boolean url_flag = request.isRequestedSessionIdFromURL();
    out.println("<p>");
    out.println("FromCookie : " + cookie_flag + "<br>");
    out.println("FromURL : " + url_flag);
    out.println("</p>");    
    
    out.println("<br/><br/>");
    out.println("<a href=\"" + eURL +"\">再表示</a>");
    out.println("</body>");
    out.println("</html>");
  }

  private void showSessionAttrs(PrintWriter out, HttpSession session) {
    out.println("<p>");
    out.println("登録されているセッションオブジェクトを表示します" + "<br/>");
    //Enumeration enum_session = session.getAttributeNames();
    //while(enum_session.hasMoreElements())
    List<String> attrs = Collections.list(session.getAttributeNames());
    for (String key : attrs) {
      String val = (String)session.getAttribute(key);
      out.println(key + " = " + val + "<br/>");
    }
    out.println("</p>");
  }
}

