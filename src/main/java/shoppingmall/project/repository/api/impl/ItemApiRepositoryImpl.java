package shoppingmall.project.repository.api.impl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.apidto.*;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.repository.api.custom.ItemApiRepositoryCustom;
import shoppingmall.project.service.ItemService;

import java.util.List;

import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QClothes.clothes;
import static shoppingmall.project.domain.item.QElectronics.electronics;
import static shoppingmall.project.domain.item.QFood.food;
import static shoppingmall.project.domain.item.QItem.item;

@Repository
public class ItemApiRepositoryImpl implements ItemApiRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public ItemApiRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    //api
    /**
     * 주어진 조건에 맞는 아이템의 리스트를 찾습니다.
     *
     * @param itemCond 아이템 검색 조건을 담고 있는 ItemCond 객체
     * @return 조건에 맞는 아이템 리스트
     */
    @Override
    public List<Item> findPriceRange(ItemCond itemCond) {
        return queryFactory.selectFrom(item)
                .where(item.price.between(itemCond.getMin(), itemCond.getMax()))
                .fetch();
    }

    /**
     * 특정 타입(dtype)과 가격 범위에 해당하는 아이템들을 찾습니다.
     *
     * @param itemCond 아이템 검색 조건(타입과 가격 범위)을 담고 있는 ItemCond 객체
     * @return 조건에 맞는 아이템 리스트
     */
    @Override
    public List<Item> findDtypePriceRange(ItemCond itemCond) {
        return queryFactory.selectFrom(item)
                .where(item.dtype.eq(itemCond.getDtype()).and(item.price.between(itemCond.getMin(), itemCond.getMax())))
                .fetch();
    }

    /**
     * 주어진 ID에 해당하는 책 정보를 업데이트합니다.
     *
     * @param id 업데이트할 책의 ID
     * @return 업데이트된 책 객체
     */
    @Override
    public Book updateBook(Long id) {
        return queryFactory.select(book)
                .from(book)
                .where((book.id.eq(id)))
                .fetchOne();
    }
    @Override
    public Clothes updateClothes(Long id) {
        return queryFactory.select(clothes)
                .from(clothes)
                .where((clothes.id.eq(id)))
                .fetchOne();
    }
    @Override
    public Electronics updateElectronics(Long id) {
        return queryFactory.select(electronics)
                .from(electronics)
                .where((electronics.id.eq(id)))
                .fetchOne();
    }

    @Override
    public Food updateFood(Long id) {
        return queryFactory.select(food)
                .from(food)
                .where((food.id.eq(id)))
                .fetchOne();
    }

    /**
     *  단건 책 정보를 반환합니다.
     * @param id 찾아야할 책의 id
     * @return
     */
    @Override
    public Book findBook(Long id) {
        return queryFactory.select(book)
                .from(book)
                .where(book.id.eq(id)).fetchOne();
    }

    @Override
    public Clothes findClothes(Long id) {
        return queryFactory.select(clothes)
                .from(clothes)
                .where(clothes.id.eq(id)).fetchOne();
    }

    @Override
    public Electronics findElectronics(Long id) {
        return queryFactory.select(electronics)
                .from(electronics)
                .where(electronics.id.eq(id)).fetchOne();
    }

    @Override
    public Food findFood(Long id) {
        return queryFactory.select(food)
                .from(food)
                .where(food.id.eq(id)).fetchOne();
    }

    /**
     * 모든 책 정보를 리스트로 반환합니다.
     *
     * @return 책 정보를 담은 BookApiDto 객체 리스트
     */
    @Override
    public List<BookApiDto> findAllBook() {
       return queryFactory.select(Projections.constructor(BookApiDto.class,
                item.id,
                item.name,
                item.price,
                item.quantity,
                book.isbn,
                book.author
                ))
                .from(item)
                .leftJoin(book)
               .on(item.id.eq(book.id))
                .fetch();
    }
    /**
     * 페이징 처리하여 모든 책 정보를 조회합니다.
     *
     * @param pageable 페이징 처리 정보
     * @return 책 정보를 담은 Page 객체
     */
    @Override
    public Page<BookApiDto> findAllBookPaging(Pageable pageable) {
        List<BookApiDto> content = queryFactory.select(Projections.constructor(BookApiDto.class,
                        item.id,
                        item.name,
                        item.price,
                        item.quantity,
                        book.isbn,
                        book.author
                ))
                .from(item)
                .leftJoin(book)
                .on(item.id.eq(book.id))
                .where(item.dtype.eq("Book"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(item.count())
                .from(item)
                .leftJoin(book)
                .on(item.id.eq(book.id))
                .where(item.dtype.eq("Book"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<ClothesApiDto> findAllClothesPaging(Pageable pageable) {
        List<ClothesApiDto> content = queryFactory.select(Projections.constructor(ClothesApiDto.class,
                        item.id,
                        item.name,
                        item.price,
                        item.quantity,
                        clothes.clothesType,
                        clothes.brand,
                        clothes.size
                ))
                .from(item)
                .leftJoin(clothes)
                .on(item.id.eq(clothes.id))
                .where(item.dtype.eq("Clothes"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(item.count())
                .from(item)
                .leftJoin(clothes)
                .on(item.id.eq(clothes.id))
                .where(item.dtype.eq("Clothes"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<ElectronicsApiDto> findAllElectronicsPaging(Pageable pageable) {
        List<ElectronicsApiDto> content = queryFactory.select(Projections.constructor(ElectronicsApiDto.class,
                        item.id,
                        item.name,
                        item.price,
                        item.quantity,
                        electronics.brand
                ))
                .from(item)
                .leftJoin(electronics)
                .on(item.id.eq(electronics.id))
                .where(item.dtype.eq("Electronics"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(item.count())
                .from(item)
                .leftJoin(electronics)
                .on(item.id.eq(electronics.id))
                .where(item.dtype.eq("Electronics"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public Page<FoodApiDto> findAllFoodPaging(Pageable pageable) {
        List<FoodApiDto> content = queryFactory.select(Projections.constructor(FoodApiDto.class,
                        item.id,
                        item.name,
                        item.price,
                        item.quantity,
                        food.brand
                ))
                .from(item)
                .leftJoin(food)
                .on(item.id.eq(food.id))
                .where(item.dtype.eq("Food"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(item.count())
                .from(item)
                .leftJoin(food)
                .on(item.id.eq(food.id))
                .where(item.dtype.eq("Food"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

    @Override
    public List<ItemApiDto> itemNameSearch(String itemName) {
         return queryFactory.select(Projections.constructor(ItemApiDto.class,
                        item.id,
                        item.name,
                        item.price,
                        item.quantity,
                        item.dtype))
                .from(item)
                .where(item.name.like("%" + itemName + "%"))
                .fetch();
    }

    /**
     * 주어진 오프셋과 한도를 사용하여 페이징 처리된 BookApiDto 목록을 조회합니다.
     *
     * <p>이 메소드는 JPQL을 사용하여 Item 엔티티와 Book 엔티티를 조인하여,
     * 책 정보를 포함하는 BookApiDto 객체의 목록을 생성합니다. 이 과정에서
     * 특정 오프셋에서 시작하여 제한된 수의 결과만 반환하도록 페이징 처리를 합니다.</p>
     *
     * @param offset 시작 위치(0부터 시작)
     * @param limit 조회할 최대 항목 수
     * @return 페이징 처리된 BookApiDto 객체의 목록
     */
    @Override
    public List<BookApiDto> jpqlPaging(int offset, int limit) {
        return entityManager.createQuery("SELECT NEW shoppingmall.project.domain.apidto.BookApiDto("
                + "item.id, "
                + "item.name, "
                + "item.price, "
                + "item.quantity, "
                + "book.isbn, "
                + "book.author) "
                + "FROM Item item "
                + "JOIN fetch Book book "
                + "ON item.id = book.id "
                , BookApiDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }


}
