package shoppingmall.project.repository.api.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.repository.api.custom.MarketApiRepositoryCustom;

import java.util.List;

import static shoppingmall.project.domain.QMarket.market;
import static shoppingmall.project.domain.QUploadFile.uploadFile;
import static shoppingmall.project.domain.item.QItem.item;

@Repository
public class MarketApiRepositoryImpl implements MarketApiRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MarketApiRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<ItemDto> findItemAndFile(List<Long> itemIds, Long userId) {

        return queryFactory.select(Projections.constructor(ItemDto.class,
                        item.id, item.name, item.price, market.orderQuantity,
                        uploadFile.uploadFileName, uploadFile.storeFileName))
                .from(market)
                .join(market.items, item)
                .join(item.uploadFiles, uploadFile)
                .where(item.id.in(itemIds).and(market.user.id.eq(userId)))
                .fetch();
        //where에서 item.id.in(itemIds)없어도 됨 한번에 장바구니 안에 userid기준으로 아이템을 찾을 수 있음


    }

    /**
     * 구매할 아이템을 찾아 반환합니다.
     * @param userId 구매할 아이템을 담은 user의 id
     * @return 장바구니에 담은 아이템의 List
     */
    @Override
    public List<ItemDto> findItemAndFileRefactor(Long userId) {

        return queryFactory.select(Projections.constructor(ItemDto.class,
                        item.id, item.name, item.price, market.orderQuantity,
                        uploadFile.uploadFileName, uploadFile.storeFileName))
                .from(market)
                .join(market.items, item)
                .join(item.uploadFiles, uploadFile)
                .where(market.user.id.eq(userId))
                .fetch();

    }



}
