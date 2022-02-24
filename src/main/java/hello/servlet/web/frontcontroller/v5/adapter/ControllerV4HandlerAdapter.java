package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        // controllerV4에 해당하는 핸들러 어뎁터 가져옴
        ControllerV4 controller = (ControllerV4) handler;

        // 고객 요청 파라미터 가져옴
        Map<String, String> paramMap = createParamMap(request);

        // 모델 생성
        HashMap<String, Object> model = new HashMap<>();

        // 실제 컨트롤러 호출(고객 요청 파라미터, 텅 빈 모델 컨트롤러에 넘김)
       String viewName = controller.process(paramMap, model);

       // 컨트롤러에서 받아온 논리 뷰 이름과 컨트롤러에서 넣어준 모델 ModelView에 세팅후 리턴
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
