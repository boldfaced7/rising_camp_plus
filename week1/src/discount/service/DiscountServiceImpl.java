package discount.service;

import discount.domain.Discount;
import discount.repository.DiscountRepository;
import member.domain.Grade;
import member.domain.Member;
import member.repository.MemberRepository;

public class DiscountServiceImpl implements DiscountService{
    private MemberRepository memberRepository;
    private DiscountRepository discountRepository;
    private int bronze = 10;
    private int silver = 20;
    private int gold = 30;

    public DiscountServiceImpl(MemberRepository memberRepository, DiscountRepository discountRepository) {
        this.memberRepository = memberRepository;
        this.discountRepository = discountRepository;
    }

    @Override
    public int calculate(Member member, int price) {
        int percent;

        if (member.getGrade() == Grade.BRONZE) percent = bronze;
        else if (member.getGrade() == Grade.SILVER) percent = silver;
        else percent = gold;

        return price * percent / 100;
    }

    @Override
    public Discount createDiscount(Member member, int price) {
        int discountingPrice = calculate(member, price);
        Discount discount = new Discount(member.getId(), price, discountingPrice);
        discountRepository.save(discount);
        return discount;
    }
    public void printDiscount(Long memberId) {
        int price = discountRepository.findByMemberId(memberId).getPrice();
        int discountingPrice = discountRepository.findByMemberId(memberId).getDiscountingPrice();

        if (memberRepository.findById(memberId).getDiscountPolicyName() == "할인 결제 방식") {
            System.out.println("결제 금액: " + (price - discountingPrice));
        }
        else {
            System.out.println("보너스: " + discountingPrice);
            System.out.println("결제 금액: " + price);
        };
    }
}
