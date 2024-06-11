package shoppingmall.project.domain.apidto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Food request")
public class FoodApiDto {

    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String brand;

    public FoodApiDto() {
    }

    public FoodApiDto(Long id, String name, int price, int quantity, String brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
    }
}
