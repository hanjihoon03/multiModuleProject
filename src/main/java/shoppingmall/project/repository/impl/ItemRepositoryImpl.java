package shoppingmall.project.repository.impl;


import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.apidto.ItemApiDto;
import shoppingmall.project.domain.apidto.ItemCond;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.exception.MyDbException;
import shoppingmall.project.repository.custom.ItemRepositoryCustom;

import java.util.ArrayList;
import java.util.List;

import static shoppingmall.project.domain.QMarket.market;
import static shoppingmall.project.domain.QUploadFile.*;
import static shoppingmall.project.domain.QUser.user;
import static shoppingmall.project.domain.item.QBook.book;
import static shoppingmall.project.domain.item.QClothes.*;
import static shoppingmall.project.domain.item.QElectronics.*;
import static shoppingmall.project.domain.item.QFood.*;
import static shoppingmall.project.domain.item.QItem.item;


@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {


    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public ItemRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

//    @Override
//    public List<BookAndFileDto> findBookWithUploadFile() {
//
//        //기준으로 다른 쿼리들도 바꾸기 ele, food, clo 전부 아래 참고해서 바꾸기
//        // @QueryProjection은 dto가 querydsl을 의존하게 되므로 Projections.bean사용.
//        //업로드 파일을 기준으로 아이템과 아이템에 해당하는 파일이 존재하면 Dto를 채워서 반환해주는 쿼리
//        //쿼리를 한 방으로 줄였으며 코드도 깔끔해지고 원래 아이템과 파일을 저장하는 부분에서 파일을 저장하는 메서드를 따로 사용했지만 메서드를 사용할 필요도 없어짐
//        //전제 조건으로 아이템은 그 아이템을 보여줄 이미지가 필요하기 때문에 아래의 쿼리로 이미지가 없는 아이템은 조건에 부합하지 않기 때문에 결과에 들어가지 않음으로 DB에서 신뢰되는 데이터만 얻을 수 있다.
//        return queryFactory.select(Projections.bean(BookAndFileDto.class,
//                        uploadFile.item.id,
//                        uploadFile.item.name,
//                        uploadFile.item.price,
//                        uploadFile.item.quantity,
//                        uploadFile.uploadFileName,
//                        uploadFile.storeFileName,
//                        Expressions.as(book.isbn, "isbn"), // 부모 클래스인 Item에서 직접 접근
//                        Expressions.as(book.author, "author") // 부모 클래스인 Item에서 직접 접근
//                ))
//                .from(uploadFile)
//                .leftJoin(item)
//                .on(uploadFile.item.id.eq(item.id))
//                .leftJoin(book)
//                .on(item.id.eq(book.id))
//                .where(item.dtype.eq("Book"))
//                .fetch();
//    }

    @Override
    public Page<BookAndFileDto> pagingBook(Pageable pageable) {

        List<BookAndFileDto> content = queryFactory.select(Projections.bean(BookAndFileDto.class,
                        uploadFile.item.id,
                        uploadFile.item.name,
                        uploadFile.item.price,
                        uploadFile.item.quantity,
                        uploadFile.uploadFileName,
                        uploadFile.storeFileName,
                        Expressions.as(book.isbn, "isbn"), // 부모 클래스인 Item에서 직접 접근
                        Expressions.as(book.author, "author") // 부모 클래스인 Item에서 직접 접근
                ))
                .from(uploadFile)
                .leftJoin(item)
                .on(uploadFile.item.id.eq(item.id))
                .leftJoin(book)
                .on(item.id.eq(book.id))
                .where(item.dtype.eq("Book"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(uploadFile.count())
                .from(uploadFile)
                .leftJoin(uploadFile.item, item)
                .where(item.dtype.eq("Book"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

//    @Override
//    public List<ClothesAndFileDto> findClothesWithUploadFile() {
//
//        //이 쿼리의 문제점은 쿼리가 2방이 나간다는 것과 리포지토리에서 dto 객체를 직접 채우는 것도 역할과 책임에서 벗어난 일이다.
//        //파일을 가져올 때 조건이 없이 가져오기 때문에 실제 아이템과 아이템의 저장된 파일이 맞지 않아도 업로드 파일에 저장된 순서대로 이미지를 보여주는 문제가 발생
//        //전제 조건이 아이템을 저장할 떄 반드시 파일을 같이 저장해야하기 때문에 파일을 기준으로 아이템을 찾아도 문제x 아래 주석 처리 된 로직들은 잘못된 로직이지만 기록상 남겼다.
//        //item과 uploadfile이 연관관계가 있다는 것을 알고 있었지만 단순하게 편리한 길을 택했고 결국 문제가 발생하게 되었다. 물론 단순하고 간결한게 좋지만 더 깊게 생각해 볼 필요를 느꼈고 테스트를 한다고 했지만 예상치 못한 일들이 항상 일어나는 것 같다.
//
//
////        List<Clothes> clothe = queryFactory
////                .selectFrom(clothes)
////                .leftJoin(clothes.uploadFiles).fetchJoin()
////                .fetch();
////
////        List<UploadFile> uploadFiles = queryFactory.selectFrom(uploadFile).fetch();
////
////        List<ClothesAndFileDto> dtos = new ArrayList<>();
////        for (Clothes clothes : clothe) {
////            ClothesAndFileDto dto = new ClothesAndFileDto(
////                    clothes.getId(),
////                    clothes.getName(),
////                    clothes.getPrice(),
////                    clothes.getQuantity(),
////                    initializeUploadFileNameClothes(clothes,uploadFiles),
////                    initializeStoreFileNameClothes(clothes,uploadFiles),
////                    clothes.getClothesType(),
////                    clothes.getBrand(),
////                    clothes.getSize()
////            );
////            dtos.add(dto);
////
////        }
////        return dtos;
//
//
//        return queryFactory.select(Projections.bean(ClothesAndFileDto.class,
//                        uploadFile.item.id,
//                        uploadFile.item.name,
//                        uploadFile.item.price,
//                        uploadFile.item.quantity,
//                        uploadFile.uploadFileName,
//                        uploadFile.storeFileName,
//                        Expressions.as(clothes.clothesType, "clothesType"), // 부모 클래스인 Item에서 직접 접근
//                        Expressions.as(clothes.brand, "brand"), // 부모 클래스인 Item에서 직접 접근
//                        Expressions.as(clothes.size, "size") // 부모 클래스인 Item에서 직접 접근
//                ))
//                .from(uploadFile)
//                .leftJoin(item)
//                .on(uploadFile.item.id.eq(item.id))
//                .leftJoin(clothes)
//                .on(item.id.eq(clothes.id))
//                .where(item.dtype.eq("Clothes"))
//                .fetch();
//
//    }

    @Override
    public Page<ClothesAndFileDto> pagingClothes(Pageable pageable) {
        List<ClothesAndFileDto> content = queryFactory.select(Projections.bean(ClothesAndFileDto.class,
                        uploadFile.item.id,
                        uploadFile.item.name,
                        uploadFile.item.price,
                        uploadFile.item.quantity,
                        uploadFile.uploadFileName,
                        uploadFile.storeFileName,
                        Expressions.as(clothes.clothesType, "clothesType"), // 부모 클래스인 Item에서 직접 접근
                        Expressions.as(clothes.brand, "brand"), // 부모 클래스인 Item에서 직접 접근
                        Expressions.as(clothes.size, "size") // 부모 클래스인 Item에서 직접 접근
                ))
                .from(uploadFile)
                .leftJoin(item)
                .on(uploadFile.item.id.eq(item.id))
                .leftJoin(clothes)
                .on(item.id.eq(clothes.id))
                .where(item.dtype.eq("Clothes"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(uploadFile.count())
                .from(uploadFile)
                .leftJoin(uploadFile.item, item)
                .where(item.dtype.eq("Clothes"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

//    @Override
//    public List<ElectronicsAndFileDto> findElectronicsWithUploadFile() {
//
//        return queryFactory.select(Projections.bean(ElectronicsAndFileDto.class,
//                        uploadFile.item.id,
//                        uploadFile.item.name,
//                        uploadFile.item.price,
//                        uploadFile.item.quantity,
//                        uploadFile.uploadFileName,
//                        uploadFile.storeFileName,
//                        Expressions.as(electronics.brand, "brand") // 부모 클래스인 Item에서 직접 접근
//                ))
//                .from(uploadFile)
//                .leftJoin(item)
//                .on(uploadFile.item.id.eq(item.id))
//                .leftJoin(electronics)
//                .on(item.id.eq(electronics.id))
//                .where(item.dtype.eq("Electronics"))
//                .fetch();
//
//    }

    @Override
    public Page<ElectronicsAndFileDto> pagingElectronics(Pageable pageable) {

        List<ElectronicsAndFileDto> content = queryFactory.select(Projections.bean(ElectronicsAndFileDto.class,
                        uploadFile.item.id,
                        uploadFile.item.name,
                        uploadFile.item.price,
                        uploadFile.item.quantity,
                        uploadFile.uploadFileName,
                        uploadFile.storeFileName,
                        Expressions.as(electronics.brand, "brand") // 부모 클래스인 Item에서 직접 접근
                ))
                .from(uploadFile)
                .leftJoin(item)
                .on(uploadFile.item.id.eq(item.id))
                .leftJoin(electronics)
                .on(item.id.eq(electronics.id))
                .where(item.dtype.eq("Electronics"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(uploadFile.count())
                .from(uploadFile)
                .leftJoin(uploadFile.item, item)
                .where(item.dtype.eq("Electronics"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

//    @Override
//    public List<FoodAndFileDto> findFoodWithUploadFile() {
//
//        return queryFactory.select(Projections.bean(FoodAndFileDto.class,
//                        uploadFile.item.id,
//                        uploadFile.item.name,
//                        uploadFile.item.price,
//                        uploadFile.item.quantity,
//                        uploadFile.uploadFileName,
//                        uploadFile.storeFileName,
//                        Expressions.as(food.brand, "brand") // 부모 클래스인 Item에서 직접 접근
//                ))
//                .from(uploadFile)
//                .leftJoin(item)
//                .on(uploadFile.item.id.eq(item.id))
//                .leftJoin(food)
//                .on(item.id.eq(food.id))
//                .where(item.dtype.eq("Food"))
//                .fetch();
//
//    }

    @Override
    public Page<FoodAndFileDto> pagingFood(Pageable pageable) {
        List<FoodAndFileDto> content = queryFactory.select(Projections.bean(FoodAndFileDto.class,
                        uploadFile.item.id,
                        uploadFile.item.name,
                        uploadFile.item.price,
                        uploadFile.item.quantity,
                        uploadFile.uploadFileName,
                        uploadFile.storeFileName,
                        Expressions.as(food.brand, "brand") // 부모 클래스인 Item에서 직접 접근
                ))
                .from(uploadFile)
                .leftJoin(item)
                .on(uploadFile.item.id.eq(item.id))
                .leftJoin(food)
                .on(item.id.eq(food.id))
                .where(item.dtype.eq("Food"))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long total = queryFactory
                .select(uploadFile.count())
                .from(uploadFile)
                .leftJoin(uploadFile.item, item)
                .where(item.dtype.eq("Food"))
                .fetchOne();

        return new PageImpl<>(content,pageable,total);
    }

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
     * 유저의 아이디를 통해 유저가 장바구니에 담은 아이템을 반환합니다.
     * @param userId 장바구니를 채운 유저의 아이디
     * @return 유저가 담은 아이템을 list로 반환
     */
    @Override
    public List<Item> findItemsByUserId(Long userId) {

        return queryFactory
                .select(item)
                .from(item)
                .innerJoin(item.markets, market)
                .innerJoin(market.user, user)
                .where(user.id.eq(userId))
                .fetch();
    }




}
