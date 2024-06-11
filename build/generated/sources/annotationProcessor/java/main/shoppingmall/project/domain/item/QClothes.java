package shoppingmall.project.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QClothes is a Querydsl query type for Clothes
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClothes extends EntityPathBase<Clothes> {

    private static final long serialVersionUID = 614674288L;

    public static final QClothes clothes = new QClothes("clothes");

    public final QItem _super = new QItem(this);

    public final StringPath brand = createString("brand");

    public final EnumPath<ClothesType> clothesType = createEnum("clothesType", ClothesType.class);

    //inherited
    public final StringPath dtype = _super.dtype;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final ListPath<shoppingmall.project.domain.Market, shoppingmall.project.domain.QMarket> markets = _super.markets;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final NumberPath<Integer> price = _super.price;

    //inherited
    public final NumberPath<Integer> quantity = _super.quantity;

    public final NumberPath<Integer> size = createNumber("size", Integer.class);

    //inherited
    public final ListPath<shoppingmall.project.domain.UploadFile, shoppingmall.project.domain.QUploadFile> uploadFiles = _super.uploadFiles;

    public QClothes(String variable) {
        super(Clothes.class, forVariable(variable));
    }

    public QClothes(Path<? extends Clothes> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClothes(PathMetadata metadata) {
        super(Clothes.class, metadata);
    }

}

