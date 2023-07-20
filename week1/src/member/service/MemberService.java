package member.service;

import member.domain.Member;

import java.util.List;

public interface MemberService {
    public Member join(Member member);
    public List<Member> findMembers();
}
