package member.service;

import member.domain.Member;
import member.repository.MemberRepository;

import java.util.List;

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member join(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }
}
