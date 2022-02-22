package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    // 테스트가 끝나면 테스트를 초기화함
    @AfterEach // 테스트가 끝난 후 실행
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Member member = new Member("hello", 20);

        // when
        Member saveMember = memberRepository.save(member);

        // then
        Member findMember = memberRepository.findById(saveMember.getId());

        // 찾은 멤버는 저장 멤버와 같아야 한다.
        assertThat(findMember).isEqualTo(saveMember);
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);

        // then
        List<Member> result = memberRepository.findAll();

        // 찾은 멤버는 저장 멤버와 같아야 한다.
        assertThat(result.size()).isEqualTo(2); // 갯수 확인
        assertThat(result).contains(member1, member2); // 데이터 확인
    }

}
