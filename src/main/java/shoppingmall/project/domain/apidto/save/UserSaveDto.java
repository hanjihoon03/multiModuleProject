package shoppingmall.project.domain.apidto.save;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import shoppingmall.project.domain.subdomain.Address;

@Data
@Schema(description = "Save User")
public class UserSaveDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String loginId;

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "사용자 나이를 입력해주세요.")
    private Integer age;

    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "zipcode를 입력해주세요.")
    private String zipcode;
    @NotBlank(message = "city를 입력해주세요.")
    private String city;
    @NotBlank(message = "street를 입력해주세요.")
    private String street;
}
