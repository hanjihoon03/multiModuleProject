package shoppingmall.project.domain.apidto.update;

import lombok.Data;

/**
 * Electronics를 수정 하기 위한 DTO
 */
@Data
public class UpdateElectronicsDto {

    private String name;
    private int price;
    private int quantity;
    private String brand;

}
