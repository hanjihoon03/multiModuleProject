package shoppingmall.project.repository.api.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.repository.api.custom.UploadApiRepositoryCustom;

import java.util.List;

import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QItem.item;

@Repository
public class UploadApiRepositoryImpl implements UploadApiRepositoryCustom {
    private final JPAQueryFactory queryFactory;


    public UploadApiRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     *  아이템 삭제에 연계되어 삭제될 파일을 삭제합니다.
     * @param itemId 삭제될 아이템의 id
     */
    @Override
    public void deleteFromItemId(Long itemId) {
        queryFactory.delete(uploadFile)
                .where(uploadFile.item.id.eq(itemId));
    }


}
