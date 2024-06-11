package shoppingmall.project.repository.api.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.apidto.UserDto;
import shoppingmall.project.domain.apidto.UserLoginIdPwDto;
import shoppingmall.project.domain.subdomain.Tier;

import java.util.List;

public interface UserApiRepositoryCustom{
    UserLoginIdPwDto findByNameAndEmail(String name, String email);
}
