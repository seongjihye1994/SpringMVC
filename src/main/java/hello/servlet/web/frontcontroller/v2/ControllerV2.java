package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {

    // MyView를 반환
    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    // 기존 v1 인터페이스는 void 타입이였다.
    // 컨트롤러가 알아서 jsp를 랜더링하게 시켰기 때문
    // v2에서는 MyView 객체를 FrontController로 전달해서
    // FrontController가 jsp를 랜더링하게 만들기 위해 MyView 타입으로 리턴턴
}
