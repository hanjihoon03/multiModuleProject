package shoppingmall.project.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.repository.api.custom.UploadApiRepositoryCustom;

public interface UploadApiRepository extends JpaRepository<UploadFile, Long>, UploadApiRepositoryCustom {
}
