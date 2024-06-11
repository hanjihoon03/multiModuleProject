package shoppingmall.project.domain.apidto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Electronics save request")
public class ElectronicsSaveApiDto {
    private String name;
    private int price;
    private int quantity;
    private String brand;

    public ElectronicsSaveApiDto() {
    }

    public ElectronicsSaveApiDto(String name, int price, int quantity, String brand) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
    }
}
