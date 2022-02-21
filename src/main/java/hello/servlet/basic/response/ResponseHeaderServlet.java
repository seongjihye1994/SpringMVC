package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // [status-line]
        // HTTP 응답 메세지의 응답 코드(상태 코드) 생성
        response.setStatus(HttpServletResponse.SC_OK); // 200 성공
        // response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 (클라이언트 오류)

        // [response-headers]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 완전히 무효화
        response.setHeader("Pragma", "no-cache"); // 과거 버전까지 캐시 무효화
        response.setHeader("my-header", "hello"); // 커스텀 헤더 작성

        // [Header 편의 메서드]
        // content(response);
        cookie(response);
        redirect(response);

        // [message Body]
        PrintWriter writer = response.getWriter();
        writer.println("안녕하세요."); // 웹 브라우저에 출력

    }

    // Header content 편의 메서드
    private void content(HttpServletResponse response) {
        // Content-Type: text/plain;charset=utf-8
        // Content-Length: 2
        // response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        // response.setContentLength(2); // (생략시 자동 생성)
    }

    // Header cookie 편의 메서드
    private void cookie(HttpServletResponse response) {
        // Set-Cookie: myCookie=good; Max-Age=600;
        // response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); // 600초
        response.addCookie(cookie);
    }

    // Header redirect 편의 메서드
    private void redirect(HttpServletResponse response) throws IOException {
        // Status Code 302
        // Location: /basic/hello-form.html
        // response.setStatus(HttpServletResponse.SC_FOUND); // 302
        // response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}
