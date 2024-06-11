package shoppingmall.project.domain.apidto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Book request")
public class BookApiDto {

    private Long id;
    private String name;
    private int price;
    private int quantity;
    private String isbn;
    private String author;

    public BookApiDto() {
    }

    public BookApiDto(Long id, String name, int price, int quantity, String isbn, String author) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isbn = isbn;
        this.author = author;
    }

}
