package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    // JSON -> 자바 객체 변환 라이브러리 Jackson, Gson 같은 JSON 라이브러리가 필요하다.
    // 클라이언트 {"username":"jeong","age":"24"} 로 요청보냄
    // 서버는 Jackson 라이브러리로 username과 age의 멤버변수를 갖는 클래스의 객체로 받을 수 있음.
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream(); // 가져 오는 방식은 일반 text 가져올 때랑 같이 바이트로 받아옴
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 바이트 코드 -> String 변환

        System.out.println("messageBody = " + messageBody);
        // messageBody = {"username": "hello", "age": 20}

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        // 요청 받은 JSON 데이터를 HelloData 객체로 자동 생성
        // 서버는 Jackson 라이브러리로 username과 age의 멤버변수를 갖는 클래스의 HelloData 객체로 받기.

        System.out.println("helloData.username = " + helloData.getUsername());
        System.out.println("helloData.age = " + helloData.getAge());

        response.getWriter().write("ok");
    }
}
