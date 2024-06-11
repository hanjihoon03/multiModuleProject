package shoppingmall.project.service;

import io.micrometer.core.annotation.Counted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.dto.PurchasePayDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.PurchaseRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    /**
     * 결제 후 결제한 정보를 저장하기 위한 로직
     * @param item 사용자가 구매한 아이템
     * @param delivery 사용자의 배송 정보
     * @param user 사용자
     */
    @Counted("order.count")
    public void addPurchase(ItemDto item, Delivery delivery,User user) {
        Purchase purchase = new Purchase(
                item.getName(),
                item.getPrice(),
                item.getQuantity(),
                user,
                delivery
        );
        purchaseRepository.save(purchase);
    }




}
