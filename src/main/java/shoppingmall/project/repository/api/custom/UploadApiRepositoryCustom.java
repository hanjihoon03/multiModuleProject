package shoppingmall.project.repository.api.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;

import java.util.List;

public interface UploadApiRepositoryCustom {
    void deleteFromItemId(Long itemId);
}
