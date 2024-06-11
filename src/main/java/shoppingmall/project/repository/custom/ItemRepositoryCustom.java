package shoppingmall.project.repository.custom;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import shoppingmall.project.domain.apidto.ItemCond;
import shoppingmall.project.domain.dto.*;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.repository.FileRepository;

import java.util.List;

public interface ItemRepositoryCustom{
//    List<BookAndFileDto> findBookWithUploadFile();
//    List<ClothesAndFileDto> findClothesWithUploadFile();
//    List<ElectronicsAndFileDto> findElectronicsWithUploadFile();
//    List<FoodAndFileDto> findFoodWithUploadFile();

    List<Item> findItemsByUserId(Long userId);
    Book findBook(Long id);
    Clothes findClothes(Long id);
    Electronics findElectronics(Long id);
    Food findFood(Long id);

    //paging
    Page<BookAndFileDto> pagingBook(Pageable pageable);
    Page<ClothesAndFileDto> pagingClothes(Pageable pageable);
    Page<FoodAndFileDto> pagingFood(Pageable pageable);
    Page<ElectronicsAndFileDto> pagingElectronics(Pageable pageable);




}
