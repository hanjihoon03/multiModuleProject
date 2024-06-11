package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.repository.custom.FileRepositoryCustom;

@Repository
public interface FileRepository extends JpaRepository<UploadFile, Long>, FileRepositoryCustom {
}
