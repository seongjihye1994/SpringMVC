package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    // 서블릿은 기본적으로 HttpServlet 클래스를 상속받는다.
    // urlPatterns에 지정된 경로로 클라이언트의 요청이 오면 이 서블릿이 호출된다.

    // 이 서블릿이 호출되면 아래의 service 메소드가 호출된다.
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 개발자가 비즈니스 로직을 적는다.
        System.out.println("HelloServlet.service");

        // 클라이언트로부터 http 요청이 오면 was(서블릿 컨테이너)가
        // HttpServletRequest, HttpServletResponse 객체를 만들어서 서블릿에 파라미터로 던져준다.
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // 서블릿의 역할은 말 그대로, http 프로토콜로 통신한 클라이언트와 서버가
        // tcp/ip 기반의 http (요청/응답)메세지를 쉽게 까서 볼 수 있도록 처리하는 객체이다.
        // http (요청/응답) 메세지를 파싱하거나, 요청이 왔을 때 서버와 연결을 맺는 등..
        // 그러한 잡다한 일들을 개발자가 모두 코딩하려면 빡세기 때문에 그런 일련의 과정들을
        // 서블릿이 처리해 도와준다.

        // /hello?username=seong 쿼리 파라미터로 넘긴 값을 요청 객체(request)에서
        // getParameter로 서블릿에서 받을 수 있음.
        String username = request.getParameter("username");
        System.out.println("username = " + username);
        // username = seong

        // http 응답 메세지 보내기
        // -- http 응답 메세지 헤더 정보 ---
        response.setContentType("text/plain"); // 단순 문자 텍스트로 응답할거야.
        response.setCharacterEncoding("utf-8"); // 문자타입은 utf-8로 응답할거야.
        // -- http 응답 메세지 바디 정보 ---
        response.getWriter().write("hello " + username); // http 응답 메세지 Body에 들어감!


    }
}
