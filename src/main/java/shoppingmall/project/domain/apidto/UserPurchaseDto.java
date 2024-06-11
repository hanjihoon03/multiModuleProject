package shoppingmall.project.domain.apidto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import shoppingmall.project.domain.subdomain.Address;

import java.time.LocalDateTime;

/**
 * user가 구매한 정보를 가져오기 위한 필요 정보를 필드를 담은 dto
 */
@Data
@Schema(description = "find user PurchaseList")
public class UserPurchaseDto {
    private Long userId;
    private LocalDateTime orderTime;
    private Address address;
    private String itemName;
    private int orderPrice;
    private int orderQuantity;

    public UserPurchaseDto() {
    }


    public UserPurchaseDto(Long userId, LocalDateTime orderTime, Address address) {
        this.userId = userId;
        this.orderTime = orderTime;
        this.address = address;
    }

    public UserPurchaseDto(Long userId, LocalDateTime orderTime, Address address, String itemName, int orderPrice, int orderQuantity) {
        this.userId = userId;
        this.orderTime = orderTime;
        this.address = address;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.orderQuantity = orderQuantity;
    }
}
