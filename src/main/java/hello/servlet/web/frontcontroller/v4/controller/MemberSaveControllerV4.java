package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        // 1. 프론트 컨트롤러가 넘겨준 request 값 꺼내기
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        // 2. 핵심 비즈니스 로직 수행
        Member member = new Member(username, age);
        memberRepository.save(member);

        // 3. 프론트 컨트롤러가 넘겨준 model에 값 넣기
        model.put("member", member);

        // 4. 뷰 논리 이름 프론트 컨트롤러로 리턴
        return "save-result";
    }
}
