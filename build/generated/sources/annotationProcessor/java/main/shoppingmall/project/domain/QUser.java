package shoppingmall.project.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 615921166L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final NumberPath<Integer> accumulatedAmount = createNumber("accumulatedAmount", Integer.class);

    public final shoppingmall.project.domain.subdomain.QAddress address;

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final ListPath<Delivery, QDelivery> deliveries = this.<Delivery, QDelivery>createList("deliveries", Delivery.class, QDelivery.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loginId = createString("loginId");

    public final ListPath<Market, QMarket> markets = this.<Market, QMarket>createList("markets", Market.class, QMarket.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final ListPath<Purchase, QPurchase> purchases = this.<Purchase, QPurchase>createList("purchases", Purchase.class, QPurchase.class, PathInits.DIRECT2);

    public final EnumPath<shoppingmall.project.domain.subdomain.Tier> tier = createEnum("tier", shoppingmall.project.domain.subdomain.Tier.class);

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new shoppingmall.project.domain.subdomain.QAddress(forProperty("address")) : null;
    }

}

