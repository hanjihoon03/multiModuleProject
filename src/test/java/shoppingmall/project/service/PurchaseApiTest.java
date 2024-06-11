package shoppingmall.project.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import shoppingmall.project.ProjectApplication;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.domain.User;
import shoppingmall.project.domain.apidto.UserPurchaseDto;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.domain.subdomain.DeliveryStatus;
import shoppingmall.project.repository.DeliveryRepository;
import shoppingmall.project.repository.ItemRepository;
import shoppingmall.project.repository.UserRepository;
import shoppingmall.project.repository.api.PurchaseApiRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ProjectApplication.class)
@Slf4j
@AutoConfigureMockMvc
public class PurchaseApiTest {

    @Autowired
    PurchaseApiRepository purchaseApiRepository;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    private MockMvc mockMvc;


    @Test
    void apiTest() throws Exception {
        //given
        Optional<Item> findItem = itemRepository.findById(1L);
        Item item = findItem.orElseThrow();
        Optional<Item> findItem1 = itemRepository.findById(2L);
        Item item1 = findItem1.orElseThrow();

        Optional<User> findUser = userRepository.findById(1L);
        User user = findUser.orElseThrow();

        Delivery delivery = new Delivery(user.getAddress(), DeliveryStatus.DELIVERY, LocalDateTime.now(),user);
        deliveryRepository.save(delivery);

        Purchase purchase = new Purchase(item.getName(),item.getPrice(),1,user,delivery);
        Purchase purchase1 = new Purchase(item1.getName(),item1.getPrice(),2,user,delivery);
        purchaseApiRepository.save(purchase);
        purchaseApiRepository.save(purchase1);

        //when
        //then
        mockMvc.perform(get("/api/purchase/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].itemName").value("B1"))
                .andExpect(jsonPath("$[0].orderPrice").value(1))
                .andExpect(jsonPath("$[0].orderQuantity").value(1))
                .andExpect(jsonPath("$[1].userId").value(1L))
                .andExpect(jsonPath("$[1].itemName").value("B2"))
                .andExpect(jsonPath("$[1].orderPrice").value(2))
                .andExpect(jsonPath("$[1].orderQuantity").value(2));
    }
}




