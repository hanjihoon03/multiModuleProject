package shoppingmall.project.domain.item;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Electronics extends Item {
    private String brand;

    public Electronics(String name, int price, int quantity, String brand) {
        super(name, price, quantity);
        this.brand = brand;
    }
    public void updateElectronics(String name, int price, int quantity, String brand) {
        updateItem(name, price, quantity);
        this.brand = brand;
    }
}
