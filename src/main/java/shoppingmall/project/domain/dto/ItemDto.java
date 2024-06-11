package shoppingmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 저장한 item과 해당하는 저장된 이미지 파일을 불러오기 위한 dto
 */
@Getter
@Setter
public class ItemDto {

    private Long id;
    private String name;
    private int price;
    private int quantity;

    private String uploadFileName;
    private String storeFileName;

    public ItemDto() {
    }

    public ItemDto(Long id, String name, int price, int quantity, String uploadFileName, String storeFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public ItemDto(Long id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public ItemDto(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
