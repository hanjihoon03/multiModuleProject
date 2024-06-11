package shoppingmall.project.domain.apidto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Food save request")
public class FoodSaveApiDto {

    private String name;
    private int price;
    private int quantity;
    private String brand;

    public FoodSaveApiDto() {
    }

    public FoodSaveApiDto(String name, int price, int quantity, String brand) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
    }
}
