package discount.domain;

public class Discount {
    private Long memberId;
    private int price;
    private int discountingPrice;
    private String discountPolicyName;

    public Discount(Long memberId, int price, int discountingPrice, String discountPolicyName) {
        this.memberId = memberId;
        this.price = price;
        this.discountingPrice = discountingPrice;
        this.discountPolicyName = discountPolicyName;
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

    public String getDiscountPolicyName() {
        return discountPolicyName;
    }

    public void setDiscountPolicyName(String discountPolicyName) {
        this.discountPolicyName = discountPolicyName;
    }

    @Override
    public String toString() {
        if (discountPolicyName == "할인 결제 방식") {
            return "결제 금액: " + (price - discountingPrice);
        } else
            return "보너스: " + discountingPrice +
                    "결제 금액: " + price;
    }
}
