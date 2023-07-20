package discount.service;

import discount.domain.Discount;
import member.domain.Member;

public interface DiscountService {
    public int calculate(Member member, int price);
    public Discount createDiscount(Member member, int price);
    public void printDiscount(Long memberId);
}
