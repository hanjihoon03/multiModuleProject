package shoppingmall.project.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * 로그인 필드에 필요한 조건과 필드
 */
@Data
public class LoginForm {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
