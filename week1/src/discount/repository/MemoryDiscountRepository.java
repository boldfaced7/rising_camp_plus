package discount.repository;

import discount.domain.Discount;

import java.util.HashMap;
import java.util.Map;

public class MemoryDiscountRepository implements DiscountRepository {
    private static final Map<Long, Discount> store = new HashMap<>();

    private static Long sequence = 0L;

    @Override
    public Discount save(Discount discount) {
        store.put(discount.getMemberId(), discount);
        return discount;
    }

    @Override
    public Discount findByMemberId(Long MemberId) {
        return store.get(MemberId);
    }
}
