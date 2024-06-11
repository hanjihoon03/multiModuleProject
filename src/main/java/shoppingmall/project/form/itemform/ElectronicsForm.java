package shoppingmall.project.form.itemform;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
/**
 * item을 저장할 때 필요한 검증 조건과 저장에 필요한 필드를 가진 form
 */
@Getter
@Setter
public class ElectronicsForm {

    private String brand;
    private String name;
    private int price;
    private int quantity;
    private MultipartFile attachFile;
}
