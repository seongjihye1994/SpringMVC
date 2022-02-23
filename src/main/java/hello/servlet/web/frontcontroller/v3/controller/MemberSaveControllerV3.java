package hello.servlet.web.frontcontroller.v3.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));
        // 서블릿 기술 사용 x
        // 즉, request.getParameter() 할 수 없음
        // request.getParameter() 처리는 front controller에서 미리 처리 한 후
        // map에 put 해서 컨트롤러 호출 시 파라미터로 넘겨준다.
        // controller는 map에 담긴 request.getParameter() 정보를
        // map.get() 으로 꺼내 사용하면 됨.

        Member member = new Member(username, age);
        memberRepository.save(member);

        // 직접 만든 model 객체에 뷰 논리 이름 넣기
        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);
        // 직접 만든 모델 타입은 Map<String, Object>
        // 논리 뷰 이름과 객체를 매핑해서 프론트 컨트롤러로 넘김.
        return mv;
    }
}
