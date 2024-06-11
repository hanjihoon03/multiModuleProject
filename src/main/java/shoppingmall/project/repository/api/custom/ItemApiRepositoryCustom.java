package shoppingmall.project.repository.api.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.apidto.*;
import shoppingmall.project.domain.item.*;

import java.util.List;

public interface ItemApiRepositoryCustom{
    List<Item> findPriceRange(ItemCond itemCond);
    List<Item> findDtypePriceRange(ItemCond itemCond);
    Book updateBook(Long id);
    Clothes updateClothes(Long id);
    Electronics updateElectronics(Long id);
    Food updateFood(Long id);
    List<BookApiDto> findAllBook();
    Page<BookApiDto> findAllBookPaging(Pageable pageable);
    Page<ClothesApiDto> findAllClothesPaging(Pageable pageable);
    Page<FoodApiDto> findAllFoodPaging(Pageable pageable);
    Page<ElectronicsApiDto> findAllElectronicsPaging(Pageable pageable);
    List<BookApiDto> jpqlPaging(int offset, int limit);
    Book findBook(Long id);
    Clothes findClothes(Long id);
    Electronics findElectronics(Long id);
    Food findFood(Long id);
    List<ItemApiDto> itemNameSearch(String itemName);
}
