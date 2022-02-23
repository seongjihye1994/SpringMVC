package hello.servlet.web.frontcontroller.v3;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Front Controller는 서블릿이다.
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    // 클라이언트 요청 URL, 요청할 컨트롤러
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // paramMap 을 만들어서
        Map<String, String> paramMap = createParamMap(request);
        // paramMap 을 컨트롤러로 넘김
        ModelView mv = controller.process(paramMap); // 컨트롤러에서 넘어온 논리이름 + members 객체

        String viewName = mv.getViewName();// 컨트롤러에서 넘어온 논리 이름 new-form 빼옴
        MyView view = viewResolver(viewName);
        // /WEB-INF/views/new-form.jsp

        // 논리 이름 + 물리 이름 합쳐진 jsp로 랜더링(위임)
        view.render(mv.getModel(), request, response);
        // 여기서 mv.getModel() 해주는 이유!
        // mv.getModel 정보(여기서는 members)를 같이 render에 넘겨줘서 render에서는 해당 값들을 뽑아서
        // request값에 setAttribute로 key, value로 넣어줘야 jsp 파일에서 쉽게
        // 사용을 할 수 있기 때문에 render에서 request에 담아서 화면단으로 넘겨주는 것
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        // Names()로 클라이언트 요청값 모두 가져와 반복문 돌리고 하나씩 꺼내서 paramMap에 key, value 로 담은 후 컨트롤러로 보내주기

        return paramMap;
    }

    // 컨트롤러에서 넘어온 논리 이름과 물리 이름을 합쳐주는 메소드
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
