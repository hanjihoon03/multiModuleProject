package shoppingmall.project.domain.apidto;

import lombok.Data;
import shoppingmall.project.domain.item.*;

@Data
public class ItemSearchDto {
    private Long id;
    private String name;
    private int price;
    private int quantity;
    private Book book;
    private Food food;
    private Clothes clothes;
    private Electronics electronics;

    public ItemSearchDto() {

    }

    public ItemSearchDto(Long id, String name, int price, int quantity, Book book, Food food, Clothes clothes, Electronics electronics) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.book = book;
        this.food = food;
        this.clothes = clothes;
        this.electronics = electronics;
    }
}
