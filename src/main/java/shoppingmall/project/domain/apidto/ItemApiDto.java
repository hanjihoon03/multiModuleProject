package shoppingmall.project.domain.apidto;

import lombok.Data;

/**
 * 실제 객체를 반환하지 않기 위한
 * item을 api에 필요한 dto로 변환
 */
@Data
public class ItemApiDto {
    private Long id;
    private String name;

    private int price;
    private int quantity;
    private String dtype;

    public ItemApiDto() {
    }

    public ItemApiDto(Long id, String name, int price, int quantity, String dtype) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.dtype = dtype;
    }
}
