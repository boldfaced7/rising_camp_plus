package discount.domain;

public class Discount {
    private Long memberId;
    private int price;
    private int discountingPrice;

    public Discount(Long memberId, int price, int discountingPrice) {
        this.memberId = memberId;
        this.price = price;
        this.discountingPrice = discountingPrice;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountingPrice() {
        return discountingPrice;
    }

    public void setDiscountingPrice(int discountingPrice) {
        this.discountingPrice = discountingPrice;
    }

    @Override
    public String toString() {
        return "Discount{}";
    }
}
