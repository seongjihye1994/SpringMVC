package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // HTTP message body에 직접 담은 데이터 꺼내기
        ServletInputStream inputStream = request.getInputStream();
        // 메세지 바디의 내용을 바이트 코드로 바로 얻을 수 있다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        // 바이트 코드를 스트링으로 변환한다. 바이트를 문자로 변환할 때는 어떤 인코딩인지 알려줘야 한다.
        // 문자 <-> 바이트도 알려줘야 함.

        System.out.println("messageBody = " + messageBody);
        // messageBody = hello!

        response.getWriter().write("ok");
    }
}
