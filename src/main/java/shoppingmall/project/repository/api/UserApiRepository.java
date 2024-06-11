package shoppingmall.project.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.User;
import shoppingmall.project.repository.api.custom.UserApiRepositoryCustom;

public interface UserApiRepository extends JpaRepository<User, Long>, UserApiRepositoryCustom {
}
