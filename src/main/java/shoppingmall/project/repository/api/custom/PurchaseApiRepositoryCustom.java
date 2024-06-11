package shoppingmall.project.repository.api.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.apidto.UserPurchaseDto;

import java.util.List;

public interface PurchaseApiRepositoryCustom {
    List<UserPurchaseDto> purchaseList(Long id);
}
