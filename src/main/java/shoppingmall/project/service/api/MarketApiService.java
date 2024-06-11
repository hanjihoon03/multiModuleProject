package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.UserRepository;
import shoppingmall.project.repository.api.MarketApiRepository;
import shoppingmall.project.repository.api.impl.MarketApiRepositoryImpl;
import shoppingmall.project.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MarketApiService {

    private final MarketApiRepository marketRepository;

    private final ItemRepository itemRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * 현재 사용자의 장바구니 정보를 반환하는 로직
     * @param userId 현재 사용자의 id
     * @return 사용자의 장바구니를 dto로 반환
     */
    @Transactional(readOnly = true)
    public List<ItemDto> purchaseItem(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        User user1 = user.orElseThrow();
        // 로그인 한 사용자의 아이디를 사용하여 구매된 상품 목록을 가져옵니다.
        List<Item> itemsByUserId = itemRepository.findItemsByUserId(userId);

        //장바구니의 아이템 리스트의 아이디들 반환
        List<Long> purchaseCartItemId = new ArrayList<>();
        for (Item item : itemsByUserId) {
            purchaseCartItemId.add(item.getId());
        }
        Tier userTier = userService.findUserTier(user1.getId());

        return marketRepository.findItemAndFile(purchaseCartItemId, userId);
    }

    /**
     * 현재 사용자의 장바구니 정보를 반환하는 로직을 리팩토링해 필요 없는 부분을 삭제
     * @param userId 현재 사용자의 id
     * @return 사용자의 장바구니를 dto로 반환
     */
    @Transactional(readOnly = true)
    public List<ItemDto> shoppingBasket(Long userId) {
        return marketRepository.findItemAndFileRefactor(userId);
    }
}
