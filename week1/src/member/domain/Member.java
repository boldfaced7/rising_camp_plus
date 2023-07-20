package member.domain;

import java.util.Objects;

public abstract class Member {
    private Long id;
    private String route;
    private String name;
    private Grade grade;
    private String discountPolicyName;

    public Member(String route, String name, Grade grade, String discountPolicyName) {
        this.route = route;
        this.name = name;
        this.grade = grade;
        this.discountPolicyName = discountPolicyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getDiscountPolicyName() {
        return discountPolicyName;
    }

    public void setDiscountPolicyName(String discountPolicyName) {
        this.discountPolicyName = discountPolicyName;
    }

    @Override
    public abstract boolean equals(Object o);
    @Override
    public abstract int hashCode();
    @Override
    public abstract String toString();
}
