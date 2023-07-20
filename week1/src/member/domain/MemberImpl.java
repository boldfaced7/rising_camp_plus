package member.domain;

import java.util.Objects;

public class MemberImpl extends Member {


    public MemberImpl(String route, String name, Grade grade, String discountPolicyName) {
        super(route, name, grade, discountPolicyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(getName(), member.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "접속 경로: " + getRoute() + '\n' +
                "이름: " + getName() + '\n' +
                "등급: " + getGrade() + '\n' +
                "결제 방식: " + getDiscountPolicyName();
    }
}
