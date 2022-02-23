package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {


    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form"; // v4 버전은 컨트롤러가 논리 뷰 이름만 전달한다.
        // 모델은 프론트 컨트롤러가 생성해서 텅 빈 객체로 컨트롤러에 파라미터로 넘겨준다.
    }
}
