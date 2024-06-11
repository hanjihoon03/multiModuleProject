package shoppingmall.project.domain.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shoppingmall.project.domain.UploadFile;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Book extends Item {
    private String isbn;
    private String author;


    public Book(String name, int price, int quantity, String isbn, String author) {
        super(name, price, quantity);
        this.isbn = isbn;
        this.author = author;
    }
    public Book(Long id, String name, int price, int quantity, String isbn, String author) {
        super(id, name, price, quantity);
        this.isbn = isbn;
        this.author = author;
    }

    public void updateBook(String name, int price, int quantity, String isbn, String author) {
        updateItem(name,price,quantity);
        this.isbn = isbn;
        this.author = author;
    }


}
