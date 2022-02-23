package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// boolean support 메소드와 ModelView handle 메소드는 프론트 컨트롤러에서 호출된다.
// 1. boolean support
//      프론트 컨트롤러에서 고객 요청을 받고 그에 해당하는
//      컨트롤러 매핑 정보를 찾은 후 그 매핑 정보에 해당하는 어뎁터를 찾는 메소드다.
// 2. ModelView handle
//      support에서 true와 컨트롤러 매핑 정보에 해당하는 어뎁터를 파라미터로 받고,
//      고객이 요청할 때 보낸 parameter 값을 가지고, 받은 어뎁터로 컨트롤러를 호출한다.
//      컨트롤러 호출 시에 parameter 값을 같이 넘김
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {


    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3); // 넘어온 컨트롤러 매핑 정보가 ControllerV3인지 확인
    }

    @Override                                                                   // support 메소드에서 true 반환하면서 컨트롤러 매핑에 해당하는 어뎁터가 넘어옴
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        // 넘어온 어뎁터는 Object 형 이기 때문에 형변환을 해준다.
        ControllerV3 controller = (ControllerV3) handler;

        // 실제 컨트롤러를 호출하는 단계이기 때문에 고객이 요청한 parameter 값도 paramMap에 넣어서 보내줘야 한다.
        Map<String, String> paramMap = createParamMap(request);

        // 실제 컨트롤러 호출 (핸들러가 호출, 이전 버전은 프론트 컨트롤러에서 직접 호출했으나, 이제는 핸들러 어답터가 호출한다.)
        ModelView mv = controller.process(paramMap);
        // 컨트롤러로에서 생성한 뷰 논리이름과 모델 리턴돼서 돌아옴.

        return mv; // 뷰 논리이름 + 모델 프론트 컨트롤러로 리턴 (v5 부터는 핸들러 어뎁터로 리턴)
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        // Names()로 클라이언트 요청값 모두 가져와 반복문 돌리고 하나씩 꺼내서 paramMap에 key, value 로 담은 후 컨트롤러로 보내주기

        return paramMap;
    }


}