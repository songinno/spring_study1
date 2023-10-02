package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    // Key - Long, Value = Member
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {

        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
        // store를 반복문으로 돌면서, member의 name이 파라미터로 들어온 name과 같은지 확인,
        // 찾으면 반환
        // findAny() : 하나라도 찾는 것
        // 끝까지 돌았는데 없으면, Optional에 null이 포함돼서 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    // 테스트 후, 데이터 clean
    public void clearStore() {
        store.clear();
    }
}
