package shoppingmall.project.domain.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * 저장한 item과 해당하는 저장된 이미지 파일을 불러오기 위한 dto
 */
@Getter
@Setter
public class FoodAndFileDto {
    private Long id;
    private String name;
    private int price;
    @Min(value = 1)
    private int quantity;
    private String brand;
    private String uploadFileName;
    private String storeFileName;



    public FoodAndFileDto() {
    }

    public FoodAndFileDto(Long id, String name, int price, int quantity, String brand, String uploadFileName, String storeFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.brand = brand;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
