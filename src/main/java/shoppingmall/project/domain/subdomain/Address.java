package shoppingmall.project.domain.subdomain;

import jakarta.persistence.Embeddable;
import lombok.Getter;


@Getter
@Embeddable
public class Address {
    private String zipcode;
    private String city;
    private String street;

    public Address() {
    }

    public Address(String zipcode, String city, String street) {
        this.zipcode = zipcode;
        this.city = city;
        this.street = street;
    }
}
