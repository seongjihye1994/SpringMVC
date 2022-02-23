package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MyHandlerAdapter {

    // 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드
    // true는 처리할 수 있고, false는 처리할 수 없다는 의미
    // 고객 요청이 v1으로 오면 v1를 처리할 수 있는 어뎁터를,
    // v3로 오면 v3를 처리할 수 있는 어뎁터가 꺼내져서 프론트 컨트롤러에 넘기고,
    // 프론트 컨트롤러는 이 어댑터를 핸들러(컨트롤러) 어댑터로 넘긴다.
    boolean supports(Object handler);

    // boolean의 결과가 true면 어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환해야 한다.
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;

}
