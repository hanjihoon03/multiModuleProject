package shoppingmall.project.domain.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import shoppingmall.project.domain.item.ClothesType;

/**
 * 저장한 item과 해당하는 저장된 이미지 파일을 불러오기 위한 dto
 */
@Getter
@Setter
public class ClothesAndFileDto {
    private Long id;
    private String name;
    private int price;
    @Min(value = 1)
    private int quantity;
    private String uploadFileName;
    private String storeFileName;
    private ClothesType clothesType;
    private String brand;
    private int size;

    public ClothesAndFileDto() {
    }

    public ClothesAndFileDto(Long id, String name, int price, int quantity, String uploadFileName, String storeFileName, ClothesType clothesType, String brand, int size) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.clothesType = clothesType;
        this.brand = brand;
        this.size = size;
    }
}
