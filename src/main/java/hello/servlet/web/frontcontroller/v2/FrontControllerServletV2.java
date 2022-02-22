package hello.servlet.web.frontcontroller.v2;


import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Front Controller는 서블릿이다.
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
// /front-controller/v2/ 하위는 모두 이 FrontControllerServletV1이 호출되게 함
public class FrontControllerServletV2 extends HttpServlet {

    // 클라이언트 요청 URL, 요청할 컨트롤러
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();

        // 다형성으로 인해 ControllerV2(부모)이 받을 수 있음
        ControllerV2 controller = controllerMap.get(requestURI);
        // 클라이언트가 요청한 URL에 맞는 value(컨트롤러)값 꺼냄

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // MemberFormControllerV2.process()
        // MemberSaveControllerV2.process()
        // MemberListControllerV2.process()
        MyView view = controller.process(request, response);// 인터페이스 오버라이딩 한 메소드 호출

        // FrontController가 MyView 객체 생성해서 jsp 랜더링하도록
        view.render(request, response);

    }}
