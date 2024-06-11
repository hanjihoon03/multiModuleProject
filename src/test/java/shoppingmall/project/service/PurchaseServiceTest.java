package shoppingmall.project.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.dto.MarketPayDto;
import shoppingmall.project.domain.dto.PurchasePayDto;
import shoppingmall.project.domain.item.Book;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.DeliveryStatus;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
class PurchaseServiceTest {
    
    @Autowired
    PurchaseService purchaseService;
    @Autowired
    PurchaseRepository repository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    MarketRepository marketRepository;
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MarketService marketService;

    @Test
    void market() {
        Book book = new Book("i",1,10,"i","i");
        itemRepository.save(book);
        Book book1 = new Book("i",2,20,"i","i");
        itemRepository.save(book1);
        Address address = new Address("1","1","1");
        User user = new User("10","1",1,"10@10","1",address, Tier.NORMAL);
        userRepository.save(user);
        Market market = new Market(1,user,book);
        Market market1 = new Market(2,user,book1);
        marketRepository.save(market);
        marketRepository.save(market1);

        PurchasePayDto purchasePayDto = marketService.payRequest(user.getId());


        System.out.println("purchasePayDto.getTotal_price() = " + purchasePayDto.getTotal_price());
        System.out.println("purchasePayDto.getQuantity() = " + purchasePayDto.getQuantity());
        System.out.println("purchasePayDto.getItemName() = " + purchasePayDto.getItemName());
        System.out.println("purchasePayDto.getUserId() = " + purchasePayDto.getUserId());


    }


}