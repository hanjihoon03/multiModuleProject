package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.repository.api.UploadApiRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadApiService {

    private final UploadApiRepository uploadApiRepository;

    /**
     * 아이템을 삭제할 때 아이템과 연관된 이미지를 삭제하기 위한 로직
     * @param itemId 삭제할 아이템의 아이디
     */
    public void deleteUploadFileFromItemId(Long itemId) {
        uploadApiRepository.deleteFromItemId(itemId);
    }

}
