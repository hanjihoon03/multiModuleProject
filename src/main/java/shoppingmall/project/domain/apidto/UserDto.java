package shoppingmall.project.domain.apidto;

import jakarta.persistence.Column;
import lombok.Data;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;

@Data
public class UserDto {
    private Long id;

    private String loginId;

    private String name;

    private Integer age;

    private String email;

    private Address address;

    private Tier tier;

    private int accumulatedAmount;

    public UserDto(Long id, String loginId, String name, Integer age, String email, Address address, Tier tier, int accumulatedAmount) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.tier = tier;
        this.accumulatedAmount = accumulatedAmount;
    }
}
