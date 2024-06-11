package shoppingmall.project.domain.apidto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Book save request")
public class BookSaveApiDto {

    private String name;
    private int price;
    private int quantity;
    private String isbn;
    private String author;

    public BookSaveApiDto() {
    }

    public BookSaveApiDto(String name, int price, int quantity, String isbn, String author) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isbn = isbn;
        this.author = author;
    }

}
