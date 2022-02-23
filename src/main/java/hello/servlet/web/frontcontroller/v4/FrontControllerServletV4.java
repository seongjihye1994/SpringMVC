package hello.servlet.web.frontcontroller.v4;


import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Front Controller는 서블릿이다.
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    // 클라이언트 요청 URL, 요청할 컨트롤러
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        // 1. 클라이언트 요청 url 받아오기
        String requestURI = request.getRequestURI();

        // 2. 클라이언트의 요청 url에 맞는 컨트롤러 호출
        ControllerV4 controller = controllerMap.get(requestURI);

        // 2_1) 만약 클라이언트의 요청 url에 맞는 컨트롤러가 없으면 404 에러 리턴
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 3. 클라이언트가 요청하며 넘겨준 parameter 값 paramMap 변수에 담기
        Map<String, String> paramMap = createParamMap(request);

        // 4. 컨트롤러에 넘겨 줄 텅빈 Model 객체 만들기
        Map<String, Object> model = new HashMap<>();

        // 5. paramMap과 model을 컨트롤러로 넘김
        String viewName = controller.process(paramMap, model);
        // 6. 컨트롤러에서 넘겨준 뷰 논리이름 viewName에 담기

        // 7. 뷰 논리 이름을 가지고 뷰 물리 이름과 합치기
        MyView view = viewResolver(viewName);
        // 8. 뷰 논리이름 + 뷰 물리이름 합친 값 view에 담기

        // 9. 논리+물리 이름 합쳐진 뷰 이름에 해당하는 jsp로 model을 함께 보내며 위임 (서버 내부에서 이동)
        view.render(model, request, response); // 컨트롤러에서 리턴해 주지 않은 model에 값이 들어있는 이유는?
        // process 메서드로 전달된 model과 process 메서드 내 model은 같은 객체를 가리키고 있다. (model의 참조값이 넘어갔기 때문)
        // 즉 프론트컨트롤러에서 new로 생성한 model의 참조값을 넘기고
        // 컨트롤러에서는 new로 모델을 생성하지 않았기 때문에 같은 model이다.

        // 여기서 model을 함께 넘겨주는 이유!
        // model 정보를 같이 render에 넘겨줘서 render에서는 해당 값들을 뽑아서
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
