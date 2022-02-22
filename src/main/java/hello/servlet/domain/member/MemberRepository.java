package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    // 회원 저장소
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    // 싱글톤으로 객체 생성 private
    private static final MemberRepository instance = new MemberRepository();

    // 싱글톤 객체로 외부에서 생성 x 무조건 하나의 객체 가져다 쓰도록
    public static MemberRepository getInstance() {
        return instance;
    }

    // 싱글톤 private
    private MemberRepository() {

    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
        // store에 있는 value 모두 꺼내서 새로운 ArrayList에 담아줌
        // ArrayList 의 값을 조작해도 store 의 값은 변경되지 않도록 하기 위해.
    }

    // 반복 테스트를 위해 DB clear
    public void clearStore() {
        store.clear();
    }


}
