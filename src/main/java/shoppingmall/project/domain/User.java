package shoppingmall.project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "users_id")
    private Long id;


    private String loginId;

    private String name;


    private Integer age;


    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Tier tier;
    //누적 구매 금액
    private int accumulatedAmount;

    @OneToMany(mappedBy = "user")
    private List<Market> markets = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Delivery> deliveries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases = new ArrayList<>();



    public User(String loginId, String name, Integer age, String email, String password, Address address, Tier tier) {
        this.loginId = loginId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
        this.tier = tier;
    }

    public User(String loginId, String name, Integer age, String email, String password, Address address, Tier tier, int accumulatedAmount) {
        this.loginId = loginId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
        this.tier = tier;
        this.accumulatedAmount = accumulatedAmount;
    }

    /**
     * 구매 금액 누적
     */
    public int addAmount(int totalPrice) {
        return this.accumulatedAmount = accumulatedAmount + totalPrice;
    }

    /**
     * 사용자가 구매한 구매 가격 누적에 따라 tier가 변경하는 로직
     * @param accumulatedAmount 누적금액 파라미터
     */
    public void upgradeTier(int accumulatedAmount) {

        if (accumulatedAmount >= 2000000) {
            this.tier = Tier.GOLD;
        } else if (accumulatedAmount >= 1500000) {
            this.tier = Tier.SILVER;
        } else if (accumulatedAmount >= 1000000){
            this.tier = Tier.BRONZE;
        }
    }
}
