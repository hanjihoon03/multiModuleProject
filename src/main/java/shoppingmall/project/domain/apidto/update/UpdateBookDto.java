package shoppingmall.project.domain.apidto.update;

import lombok.Data;

/**
 * Book을 수정 하기 위한 DTO
 */
@Data
public class UpdateBookDto {

    private String name;
    private int price;
    private int quantity;
    private String isbn;
    private String author;

}
