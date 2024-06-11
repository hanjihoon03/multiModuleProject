package shoppingmall.project.domain.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import shoppingmall.project.domain.subdomain.Tier;

@Data
public class MarketPayDtoV2 {

    private Long id;
    private String name;
    private int price;
    private int orderQuantity;
    private String uploadFileName;
    private String storeFileName;


    public MarketPayDtoV2() {
    }

    public MarketPayDtoV2(Long id, String name, int price, int orderQuantity, String uploadFileName, String storeFileName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.orderQuantity = orderQuantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
