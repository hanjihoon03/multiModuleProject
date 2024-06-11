package shoppingmall.project.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1845745429L;

    public static final QItem item = new QItem("item");

    public final StringPath dtype = createString("dtype");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<shoppingmall.project.domain.Market, shoppingmall.project.domain.QMarket> markets = this.<shoppingmall.project.domain.Market, shoppingmall.project.domain.QMarket>createList("markets", shoppingmall.project.domain.Market.class, shoppingmall.project.domain.QMarket.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final ListPath<shoppingmall.project.domain.UploadFile, shoppingmall.project.domain.QUploadFile> uploadFiles = this.<shoppingmall.project.domain.UploadFile, shoppingmall.project.domain.QUploadFile>createList("uploadFiles", shoppingmall.project.domain.UploadFile.class, shoppingmall.project.domain.QUploadFile.class, PathInits.DIRECT2);

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

