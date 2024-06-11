package shoppingmall.project.domain.apidto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "find LoginId, Password")
public class UserLoginIdPwDto {
    private String loginId;
    private String password;
}
