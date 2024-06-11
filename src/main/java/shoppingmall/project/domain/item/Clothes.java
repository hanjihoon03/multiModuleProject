package shoppingmall.project.domain.item;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Clothes extends Item {
    @Enumerated(EnumType.STRING)
    private ClothesType clothesType;
    private String brand;
    private int size;

    public Clothes(String name, int price, int quantity, ClothesType clothesType, String brand, int size) {
        super(name, price, quantity);
        this.clothesType = clothesType;
        this.brand = brand;
        this.size = size;
    }

    public void updateClothes(String name, int price, int quantity, ClothesType clothesType, String brand, int size) {
        updateItem(name, price, quantity);
        this.clothesType = clothesType;
        this.brand = brand;
        this.size = size;
    }
}
