package shoppingmall.project.domain.dto;


import lombok.Data;

@Data
public class MarketPayDto {

    private Long id;
    private String name;
    private int price;
    private int orderQuantity;

}
