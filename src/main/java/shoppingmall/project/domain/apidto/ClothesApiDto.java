package shoppingmall.project.domain.apidto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import shoppingmall.project.domain.item.ClothesType;

@Data
@Schema(description = "Clothes request")
public class ClothesApiDto {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private ClothesType clothesType;
    private String brand;
    private int size;

    public ClothesApiDto() {
    }

    public ClothesApiDto(Long id, String name, int price, int quantity, ClothesType clothesType, String brand, int size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.clothesType = clothesType;
        this.brand = brand;
        this.size = size;
    }
}
