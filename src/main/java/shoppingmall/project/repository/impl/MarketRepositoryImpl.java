package shoppingmall.project.repository.impl;


import com.querydsl.core.types.Projections;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.dto.MarketPayDto;
import shoppingmall.project.domain.dto.MarketPayDtoV2;
import shoppingmall.project.repository.custom.MarketRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

import static shoppingmall.project.domain.QMarket.*;
import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.QUser.*;
import static shoppingmall.project.domain.item.QItem.*;

@Repository
public class MarketRepositoryImpl implements MarketRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MarketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    //리펙토링 전 사용 쿼리
    @Override
    public List<ItemDto> findItemAndFile(List<Long> itemIds,Long userId) {

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
     * 유저가 장바구니에 추가한 아이템 중 하나를 삭제합니다.
     * @param itemId 삭제할 아이템 아이디
     * @param userId 장바구니를 가진 유저의 아이디
     */
    @Override
    public void deleteMarketOfItem(Long itemId, Long userId) {
        queryFactory.delete(market)
                .where(market.user.id.eq(userId).and(market.items.id.eq(itemId)))
                .execute();
    }

    /**
     * 해당하는 유저의 장바구니를 모두 삭제합니다.
     * @param userId 삭제할 유저의 id
     */
    @Override
    public void deleteMarketOfUser(Long userId) {
        queryFactory.delete(market)
                .where(market.user.id.eq(userId))
                .execute();
    }

    /**
     * 장바구니 목록을 결재하기 위한 정보만 반환하는 쿼리
     * @param id 로그인 된 유저의 id
     * @return 필요한 장바구니의 정보를 담은 dto의 list를 반환
     */
    @Override
    public List<MarketPayDto> shoppingBasket(Long id) {
        return queryFactory.select(Projections.bean(MarketPayDto.class,
                market.user.id,
                market.items.name,
                item.price,
                market.orderQuantity))
                .from(market)
                .leftJoin(market.items,item)
                .where(market.user.id.eq(id))
                .fetch();
    }

    /**
     * 장바구니의 목록을 사용자에게 보여주기 위한 쿼리
     * @param id 장바구니 속 유저가 담은 아이템을 모두 반환하기 위한 user의 id
     * @return 유저가 담은 아이템과 아이템의 이미지를 담은 dto의 List
     */
    @Override
    public List<MarketPayDtoV2> shoppingBasketV2(Long id) {

        return queryFactory.select(Projections.constructor(MarketPayDtoV2.class,
                        market.items.id,
                        market.items.name,
                        item.price,
                        market.orderQuantity,
                        uploadFile.uploadFileName,
                        uploadFile.storeFileName))
                .from(market)
                .join(market.items,item)
                .join(item.uploadFiles, uploadFile)
                .where(market.user.id.eq(id))
                .fetch();
    }





}
