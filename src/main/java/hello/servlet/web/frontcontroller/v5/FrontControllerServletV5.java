package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // 기존 컨트롤러 매핑
    // private Map<String, ControllerV4> controllerMap = new HashMap<>(); // ControllerV4 타입만 지원

    // v5 버전 핸들러(컨트롤러) 매핑
    private final Map<String, Object> handlerMappingMap = new HashMap<>(); // 모든 Object 타입 지원
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();
    // 어뎁터가 여러개 담겨있고, 그 중에서 맞는 어뎁터를 찾아 써야함.

    public FrontControllerServletV5() {
        initHandlerMappingMap(); // 1. 클라이언트가 요청한 url과 맞는 컨트롤러를 매핑한다.
        initHandlerAdapters(); // 2. 알맞은 어뎁터를 넣어준다.

    }

    private void initHandlerMappingMap() {
        // v3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // v4 추가
       handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        // ControllerV3HandlerAdapter 어뎁터 넣어놓기
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        // ControllerV4HandlerAdapter 어뎁터 넣어놓기
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV5.service");

        // 0. 고객 요청 -> 서블릿 매핑

        // 1. 핸들러 매핑 정보 찾기 (v5 구조 1번)
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 핸들러 어뎁터 목록 조회해서 컨트롤러 매핑 정보에 해당하는 핸들러 어뎁터 찾기 (v5 사진 구조 2번)
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        // ControllerV3HandlerAdapter

        // 3_1) 핸들러 어뎁터를 호출 (v5 사진 구조 3번)
        ModelView mv = adapter.handle(request, response, handler);
        // ControllerV3HandlerAdapter.handle

        // 3_2) 핸들러 어답터에서 실제 비즈니스 로직을 처리하는 컨트롤러를 호출함 (v5 사진 구조 4번)

        // 3_3) 컨트롤러에서 핸들러 어댑터로 뷰 논리이름과 모델 반환

        // 3_4) 핸들러 어댑터에서 프론트 컨트롤러로 뷰 논리이름과 모델 반환 (v5 사진 구조 5번)

            // 3_1) 에서의 ModelView mv 에 뷰 논리이름과 모델이 반환됨.

        // 4. 핸들러 어뎁터에서 넘어온 논리 이름 new-form 빼옴
        String viewName = mv.getViewName();

        // 5. 핸들러 어뎁터에서 넘어온 논리 이름 new-form 빼옴
        MyView view = viewResolver(viewName); // (v5 사진 구조 6번)

        // 6. 논리 이름 + 물리 이름 합쳐진 jsp로 랜더링(위임)
        view.render(mv.getModel(), request, response); // (v5 사진 구조 7번)
        // 여기서 mv.getModel() 해주는 이유!
        // mv.getModel 정보(여기서는 members)를 같이 render에 넘겨줘서 render에서는 해당 값들을 뽑아서
        // request값에 setAttribute로 key, value로 넣어줘야 jsp 파일에서 쉽게
        // 사용을 할 수 있기 때문에 render에서 request에 담아서 화면단으로 넘겨주는 것
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        // MemberFormControllerV3 또는 MemberSaveControllerV3 또는 MemberListControllerV3
        for (MyHandlerAdapter adapter : handlerAdapters) {
            // ControllerV3HandlerAdapter 의 supports 를 호출함.
            if (adapter.supports(handler)) { // if 문이 참이되면
                return adapter; // 어뎁터를 리턴해줌
                // ControllerV3HandlerAdapter 반환됨.
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI(); // 고객 요청 url 찾기
        return handlerMappingMap.get(requestURI);
    }

    // 컨트롤러에서 넘어온 논리 이름과 물리 이름을 합쳐주는 메소드
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
