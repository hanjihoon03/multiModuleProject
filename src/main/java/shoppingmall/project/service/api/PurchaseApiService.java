package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.apidto.UserPurchaseDto;
import shoppingmall.project.repository.api.PurchaseApiRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PurchaseApiService {

    private final PurchaseApiRepository purchaseApiRepository;

    /**
     * 사용자의 아이디로 구매한 목록을 반환하는 로직
     * @param id 구매 목록을 확인할 유저의 id
     * @return 구매목록 반환
     */
    @Transactional(readOnly = true)
    public List<UserPurchaseDto> purchaseList(Long id) {
    return purchaseApiRepository.purchaseList(id);
    }

}
