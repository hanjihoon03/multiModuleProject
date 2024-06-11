package shoppingmall.project.repository.custom;

import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.repository.FileRepository;

public interface FileRepositoryCustom {
    UploadFile findByItemId(Long id);
    void deleteFromItemId(Long id);
}
