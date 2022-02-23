package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.List;
import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();


    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {

        // 1. 비즈니스 로직 수행
        List<Member> members = memberRepository.findAll();

        // 2. 프론트 컨트롤러에서 넘겨준 텅 빈 모델에 값 넣기
        model.put("members", members);

        // 3. 뷰 논리 이름 프론트 컨트롤러에 리턴하기기
       return "members";
    }

}
