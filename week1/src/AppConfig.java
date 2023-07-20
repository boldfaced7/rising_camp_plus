import discount.repository.DiscountRepository;
import discount.repository.MemoryDiscountRepository;
import discount.service.DiscountService;
import discount.service.DiscountServiceImpl;
import member.repository.MemberRepository;
import member.repository.MemoryMemberRepository;
import member.service.MemberService;
import member.service.MemberServiceImpl;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    public DiscountService discountService() {
        return new DiscountServiceImpl(memberRepository(), discountRepository());
    }
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    public DiscountRepository discountRepository() {return new MemoryDiscountRepository();
    }
}
