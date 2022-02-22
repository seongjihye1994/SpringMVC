package hello.servlet.web.frontcontroller.v1;


import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Front Controller는 서블릿이다.
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
// /front-controller/v1/ 하위는 모두 이 FrontControllerServletV1이 호출되게 함
public class FrontControllerServletV1 extends HttpServlet {

    // 클라이언트 요청 URL, 요청할 컨트롤러
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        // 클라이언트가 /front-controller/v1/members/new-form 요청하면, MemberFormControllerV1 컨트롤러 호출
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();
        // 클라이언트 요청 URL을 받을 수 있음
        // ex) /front-controller/v1/members/new-form

        // 다형성으로 인해 ControllerV1(부모)이 받을 수 있음
        ControllerV1 controller = controllerMap.get(requestURI);
        // 클라이언트가 요청한 URL에 맞는 value(컨트롤러)값 꺼냄

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // MemberFormControllerV1.process()
        // MemberSaveControllerV1.process()
        // MemberListControllerV1.process()
        controller.process(request, response); // 인터페이스 오버라이딩 한 메소드 호출

    }}
