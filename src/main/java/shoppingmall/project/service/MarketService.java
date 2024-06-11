package shoppingmall.project.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.additional.web.session.SessionConst;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.domain.dto.MarketPayDto;
import shoppingmall.project.domain.dto.MarketPayDtoV2;
import shoppingmall.project.domain.dto.PurchasePayDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.*;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MarketService {

    private final MarketRepository marketRepository;

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public void addToCart(Long itemId, int quantity, HttpSession session, Item item) {
        session.setAttribute("itemId", itemId);
        session.setAttribute("quantity", quantity);

        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);

        Market market = new Market(quantity, loginUser, item);
        //세션에 장바구니로 추가한 아이템정보 추가
        session.setAttribute(SessionConst.SHOPPING_CART,market);
        marketRepository.save(market);
    }
    @Transactional(readOnly = true)
    public List<MarketPayDtoV2> purchaseItem(Long userId) {
        return marketRepository.shoppingBasketV2(userId);
    }

    @Transactional(readOnly = true)
    public List<ItemDto> purchaseItemV2(HttpSession session) {
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        if (loginUser == null) {
            log.error("로그인 사용자를 찾을 수 없습니다.");
            return null;
        }

        // 로그인 한 사용자의 아이디를 사용하여 구매할 상품 목록을 가져옵니다.
        List<Item> itemsByUserId = itemRepository.findItemsByUserId(loginUser.getId());

        //장바구니의 아이템 리스트의 아이디들 반환
        List<Long> purchaseCartItemId = new ArrayList<>();
        for (Item item : itemsByUserId) {
            purchaseCartItemId.add(item.getId());
        }
        //장바구니 리스트의 아이디에 대한 아이템 반환
        return marketRepository.findItemAndFile(purchaseCartItemId,loginUser.getId());
    }





    public void deleteMarketUser(Long id){
        marketRepository.deleteMarketOfUser(id);
    }
    public void deleteMarketItem(Long id, Long userId) {
        marketRepository.deleteMarketOfItem(id, userId);
    }


    /**
     * user id를 받아 구매 목록을 결제하기 위한 메서드
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public PurchasePayDto payRequest(Long id) {
        try {
            List<MarketPayDto> marketPay = marketRepository.shoppingBasket(id);
            if (marketPay.isEmpty()) {
                log.info("구매 목록이 없습니다.");
                return null;
            }

            PurchasePayDto purchasePayDto = new PurchasePayDto();
            int pur_total_price = 0;
            int pur_quantity = 0;
            int count = 0;
            for (MarketPayDto marketPayDto : marketPay) {
                pur_total_price += marketPayDto.getOrderQuantity() * marketPayDto.getOrderQuantity();
                pur_quantity += marketPayDto.getOrderQuantity();
                count ++;
            }

            purchasePayDto.setItemName(marketPay.get(0).getName() + "외 " + --count + "개"); // 첫 번째 아이템의 이름으로 설정
            purchasePayDto.setUserId(marketPay.get(0).getId());
            purchasePayDto.setTotal_price(String.valueOf(pur_total_price));
            purchasePayDto.setQuantity(String.valueOf(pur_quantity));
            return purchasePayDto;
        } catch (Exception e) {
            log.error("구매 목록을 조회하는 중 오류가 발생했습니다.", e);
            return null;
        }
    }


    public int purchaseTotalPriceV2(List<ItemDto> itemDto, User user) {
        int totalPrice = 0;

        for (ItemDto dto : itemDto) {
            totalPrice += dto.getPrice() * dto.getQuantity();
        }

        totalPrice = discountLogic(user, totalPrice);

        return totalPrice;

    }




    public int purchaseTotalPrice(List<MarketPayDtoV2> items, User user) {
        int totalPrice = 0;
        for (MarketPayDtoV2 item : items) {
            totalPrice += item.getPrice() * item.getOrderQuantity();
        }
        totalPrice = discountLogic(user, totalPrice);

        return totalPrice;
    }
    public int discountAmount(int totalPrice, Tier tier) {
        int discount = 0;

        if (tier == Tier.BRONZE) {
            totalPrice = (int) (totalPrice / (1 - Tier.BRONZE.getValue()));
            discount = (int) (totalPrice * Tier.BRONZE.getValue());
        } else if (tier == Tier.SILVER) {
            totalPrice = (int) (totalPrice / (1 - Tier.SILVER.getValue()));
            discount = (int) (totalPrice * Tier.SILVER.getValue());
        } else if (tier == Tier.GOLD) {
            totalPrice = (int) (totalPrice / (1 - Tier.GOLD.getValue()));
            discount = (int) (totalPrice * Tier.GOLD.getValue());
        }

        return discount;
    }

    private int discountLogic(User user, int totalPrice) {
        int discountAmount = 0;
        Optional<User> optionalUser = userRepository.findById(user.getId());
        User findUser = optionalUser.orElseThrow(null);

        if (findUser.getTier() == Tier.BRONZE) {
            discountAmount = (int) (totalPrice * Tier.BRONZE.getValue());
            totalPrice -= discountAmount;
        } else if (findUser.getTier() == Tier.SILVER) {
            discountAmount = (int) (totalPrice * Tier.SILVER.getValue());
            totalPrice -= discountAmount;
        } else if (findUser.getTier() == Tier.GOLD) {
            discountAmount = (int) (totalPrice * Tier.GOLD.getValue());
            totalPrice -= discountAmount;
        }
        return totalPrice;
    }

}
