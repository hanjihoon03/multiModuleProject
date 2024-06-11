package shoppingmall.project;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.domain.subdomain.Address;
import shoppingmall.project.domain.subdomain.Tier;
import shoppingmall.project.repository.FileRepository;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.UserRepository;

/**
 * 테스트용 initData
 */
@Component
@RequiredArgsConstructor
public class InitData {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final FileRepository fileRepository;

    @EventListener(value = ApplicationReadyEvent.class)
    @Transactional
    public void initUser() {
        Address address = new Address("1","1","1");
        User user = new User("1","1",1,"1@1","1",address, Tier.NORMAL);
        userRepository.save(user);
        Address adminaddress = new Address("1","1","1");
        User admin = new User("admin","1",1,"2@2","1",address, Tier.NORMAL);
        userRepository.save(admin);
    }
    @EventListener(value = ApplicationReadyEvent.class)
    @Transactional
    public void initItem() {
        for (int i = 1; i <= 300; i++) {
        Book book = new Book("B"+ i,i,i,"i","i");
        itemRepository.save(book);
        UploadFile uploadFile = new UploadFile("book1.png","2b809c46-2c88-4c54-a5a9-8ac33a99e3f6.png",book);
        fileRepository.save(uploadFile);
        }
        for (int i = 1; i <= 1000; i++) {
            Clothes clothes = new Clothes("C"+ i,i,i, ClothesType.PANTS,"i",1);
            itemRepository.save(clothes);
            UploadFile uploadFile = new UploadFile("book1.png","2b809c46-2c88-4c54-a5a9-8ac33a99e3f6.png",clothes);
            fileRepository.save(uploadFile);
        }
        for (int i = 1; i <= 300; i++) {
            Electronics electronics = new Electronics("E"+ i,i,i,"i");
            itemRepository.save(electronics);
            UploadFile uploadFile = new UploadFile("book1.png","2b809c46-2c88-4c54-a5a9-8ac33a99e3f6.png",electronics);
            fileRepository.save(uploadFile);
        }
        for (int i = 1; i <= 300; i++) {
            Food food = new Food("F"+ i,i,i,"i");
            itemRepository.save(food);
            UploadFile uploadFile = new UploadFile("book1.png","2b809c46-2c88-4c54-a5a9-8ac33a99e3f6.png",food);
            fileRepository.save(uploadFile);
        }
    }
}
