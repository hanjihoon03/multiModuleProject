package shoppingmall.project.domain.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * shoppingmall.project.domain.dto.QBookAndFileDto is a Querydsl Projection type for BookAndFileDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QBookAndFileDto extends ConstructorExpression<BookAndFileDto> {

    private static final long serialVersionUID = 1595944359L;

    public QBookAndFileDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<Integer> price, com.querydsl.core.types.Expression<Integer> quantity, com.querydsl.core.types.Expression<String> uploadFileName, com.querydsl.core.types.Expression<String> storeFileName, com.querydsl.core.types.Expression<String> isbn, com.querydsl.core.types.Expression<String> author) {
        super(BookAndFileDto.class, new Class<?>[]{long.class, String.class, int.class, int.class, String.class, String.class, String.class, String.class}, id, name, price, quantity, uploadFileName, storeFileName, isbn, author);
    }

}

