package shoppingmall.project.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.UploadFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    private String name;

    private int price;
    private int quantity;
    @Column(name = "dtype", insertable = false, updatable = false)
    private String dtype;


    @OneToMany(mappedBy = "items")
    private List<Market> markets = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<UploadFile> uploadFiles = new ArrayList<>();




    public Item(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public Item(Long id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * 구매 후 수량 맞추는 메서드
     */
    public void purchaseAfterQuantity(int quantity) {

        this.quantity = this.getQuantity() - quantity;
    }

    public Item(Long id, String name, int price, int quantity, String dtype, List<Market> markets, List<UploadFile> uploadFiles) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.dtype = dtype;
        this.markets = markets;
        this.uploadFiles = uploadFiles;
    }

    public void updateItem(String name,int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}
