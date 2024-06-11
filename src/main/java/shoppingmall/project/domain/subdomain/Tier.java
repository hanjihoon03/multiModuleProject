package shoppingmall.project.domain.subdomain;

public enum Tier {
    /**
     * 구매에 따른 할인 적용
     * Bronze = 100만 이상
     * Silver = 150만 이상
     * Gold  = 200만 이상
     */
    BRONZE(0.05), SILVER(0.07), GOLD(0.1), NORMAL(0);

    private final double value;

    Tier(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Tier{" +
                "value=" + value +
                '}';
    }
}
