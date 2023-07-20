package discount.repository;

import discount.domain.Discount;

public interface DiscountRepository {
    public Discount save(Discount discount);
    public Discount findByMemberId(Long MemberId);
}
