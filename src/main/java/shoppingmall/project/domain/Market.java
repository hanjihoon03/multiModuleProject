package shoppingmall.project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import lombok.Getter;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.DeliveryStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Market {

    @Id @GeneratedValue
    @Column(name = "market_id")
    private Long id;

    private int orderQuantity; //주문 수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item items;

    public Market() {
    }

    public Market(int orderQuantity, User user, Item items) {
        this.orderQuantity = orderQuantity;
        this.user = user;
        this.items = items;
    }
}
