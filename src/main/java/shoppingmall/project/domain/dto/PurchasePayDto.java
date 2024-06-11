package shoppingmall.project.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PurchasePayDto {
    private Long userId;
    private String itemName;
    private String total_price;
    private String quantity;
}
