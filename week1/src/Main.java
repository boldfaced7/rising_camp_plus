import discount.domain.Discount;
import discount.service.DiscountService;
import member.domain.Grade;
import member.domain.Member;
import member.domain.MemberImpl;
import member.service.MemberService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 세일 서비스 객체 생성
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        DiscountService discountService = appConfig.discountService();

        // 고객 객체 생성
        Member member1 = new MemberImpl("Web", "이름6", Grade.GOLD, "할인 결제 방식");
        Member member2 = new MemberImpl("Mobile", "이름7", Grade.BRONZE, "보너스 결제 방식");

        // 고객 리스트 생성, 추가
        member1 = memberService.join(member1);
        member2 = memberService.join(member2);

        Discount discount1 = discountService.createDiscount(member1, 10000);
        Discount discount2 = discountService.createDiscount(member2, 10000);

        // 고객 정보 조회 & 가격 계산
        List<Member> members = memberService.findMembers();
        for (Member member : members) {
            System.out.println(member);
            discountService.printDiscount(member.getId());
            System.out.println("=================");
        }
    }
}