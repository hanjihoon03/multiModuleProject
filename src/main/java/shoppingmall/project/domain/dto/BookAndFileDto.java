package shoppingmall.project.domain.dto;


import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;


/**
 * 저장한 item과 해당하는 저장된 이미지 파일을 불러오기 위한 dto
 */
@Setter
@Getter
public class BookAndFileDto {

    private Long id;
    private String isbn;
    private String author;
    private String name;

    private int price;
    @Min(value = 1)
    private Integer quantity;
    private String uploadFileName;
    private String storeFileName;
    private MultipartFile attachFile;

    public BookAndFileDto() {
    }

    @QueryProjection // QueryDSL이 생성자를 찾을 수 있도록 QueryDSL Annotation을 추가합니다.
    public BookAndFileDto(Long id, String name, Integer price, Integer quantity,
                          String uploadFileName, String storeFileName,
                          String isbn, String author) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.isbn = isbn;
        this.author = author;
    }




    public BookAndFileDto(Long id, String isbn, String author, String name, int price, Integer quantity) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public BookAndFileDto(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public BookAndFileDto(Long id, String isbn, String author, String name, int price, Integer quantity, String uploadFileName, String storeFileName) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public BookAndFileDto(Long id, String isbn, String author, String name, int price, Integer quantity, String uploadFileName, String storeFileName, MultipartFile attachFile) {
        this.id = id;
        this.isbn = isbn;
        this.author = author;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.attachFile = attachFile;
    }
}
