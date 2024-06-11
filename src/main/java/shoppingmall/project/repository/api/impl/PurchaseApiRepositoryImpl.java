package shoppingmall.project.repository.api.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.*;
import shoppingmall.project.domain.apidto.UserPurchaseDto;
import shoppingmall.project.repository.api.custom.PurchaseApiRepositoryCustom;

import java.util.List;

import static shoppingmall.project.domain.QDelivery.*;
import static shoppingmall.project.domain.QPurchase.*;
import static shoppingmall.project.domain.QUser.*;

@Repository
public class PurchaseApiRepositoryImpl implements PurchaseApiRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    public PurchaseApiRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * user가 구매를 완료한 구매 완료 정보를 반환합니다.
     * @param id 구매 목록을 가진 user의 id
     * @return 구매 정보들의 list
     */
    @Override
    public List<UserPurchaseDto> purchaseList(Long id) {
        return queryFactory.select(Projections.constructor(UserPurchaseDto.class,
                purchase.user.id,
                delivery.orderTime,
                delivery.address,
                purchase.itemName,
                purchase.orderPrice,
                purchase.orderQuantity
                ))
                .from(purchase)
                .innerJoin(purchase.delivery,delivery)
                .innerJoin(purchase.user,user)
                .where(purchase.user.id.eq(id))
                .fetch();
    }
}
