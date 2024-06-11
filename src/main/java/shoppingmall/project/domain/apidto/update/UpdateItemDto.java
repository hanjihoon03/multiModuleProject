package shoppingmall.project.domain.apidto.update;

import lombok.Data;

/**
 * item을 업데이트 하기 위해 필요한 필드만 가진 dto
 */
@Data
public class UpdateItemDto {

    private String name;

    private int price;
    private int quantity;
    public UpdateItemDto() {
    }

    public UpdateItemDto(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

}
