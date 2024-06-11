package shoppingmall.project.form.itemform;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shoppingmall.project.domain.item.ClothesType;
/**
 * item을 저장할 때 필요한 검증 조건과 저장에 필요한 필드를 가진 form
 */
@Setter
@Getter
public class ClothesForm {

    private ClothesType clothesType;
    private String brand;
    private int size;

    private String name;
    private int price;
    private int quantity;
    private MultipartFile attachFile;
}
