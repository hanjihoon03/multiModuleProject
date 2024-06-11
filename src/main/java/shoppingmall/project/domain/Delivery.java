package shoppingmall.project.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;
    @OneToOne(mappedBy = "delivery")
    private Market market;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDateTime orderTime;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @OneToMany(mappedBy = "delivery")
    private List<Purchase> purchases = new ArrayList<>();
    public Delivery(Address address, DeliveryStatus status, LocalDateTime orderTime) {
        this.address = address;
        this.status = status;
        this.orderTime = orderTime;
    }

    public Delivery(Address address, DeliveryStatus status, LocalDateTime orderTime, User user) {
        this.address = address;
        this.status = status;
        this.orderTime = orderTime;
        this.user = user;
    }
}
