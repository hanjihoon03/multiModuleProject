package shoppingmall.project.domain.apidto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import shoppingmall.project.domain.item.ClothesType;

@Data
@Schema(description = "Clothes save request")
public class ClothesSaveApiDto {
    private String name;
    private int price;
    private int quantity;
    private ClothesType clothesType;
    private String brand;
    private int size;

    public ClothesSaveApiDto() {
    }

    public ClothesSaveApiDto(String name, int price, int quantity, ClothesType clothesType, String brand, int size) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.clothesType = clothesType;
        this.brand = brand;
        this.size = size;
    }
}
