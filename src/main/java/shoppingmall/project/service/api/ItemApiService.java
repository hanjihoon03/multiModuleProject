package shoppingmall.project.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.apidto.*;
import shoppingmall.project.domain.apidto.save.BookSaveApiDto;
import shoppingmall.project.domain.apidto.save.ClothesSaveApiDto;
import shoppingmall.project.domain.apidto.save.ElectronicsSaveApiDto;
import shoppingmall.project.domain.apidto.save.FoodSaveApiDto;
import shoppingmall.project.domain.apidto.update.*;
import shoppingmall.project.domain.item.*;
import shoppingmall.project.repository.api.ItemApiRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemApiService {

    private final ItemApiRepository ItemApiRepository;

    /**
     * 찾은 모든 아이템의 원본 객체는 숨기고 API에 반한하기 위해 DTO로 변환하는 로작
     * @return 찾은 아이템이 변환된 아이템 dto의 리스트
     */
    @Transactional(readOnly = true)
    public List<ItemApiDto> findAllItem() {
        List<Item> allItem = ItemApiRepository.findAll();

        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                        }).toList();
    }
    @Transactional(readOnly = true)
    public List<ItemApiDto> findAllDtype(String dtype) {
        List<Item> allItem = ItemApiRepository.findByDtype(dtype);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }

    /**
     * 아이템을 찾을 때 금액의 최소 값과 최대 값 사이 아이템을 찾아 dto로 변환해주는 로직
     * @param min 아이템의 최소 값
     * @param max 아이템의 최대 값
     * @return 찾은 아이템을 변환한 dto List
     */
    @Transactional(readOnly = true)
    public List<ItemApiDto> findPriceRange(int min, int max) {
        ItemCond itemCond = new ItemCond(min, max);
        List<Item> allItem = ItemApiRepository.findPriceRange(itemCond);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }

    /**
     * 아이템을 찾을 때 타입에 따른 아이템을 찾고 타입에 따른 아이템의 최소 값과 최대 값 사이 아이템을 찾고 dto로 변환하는 로직
     * @param dtype 찾을 타입
     * @param min 아이템의 최소 값
     * @param max 아이템의 최대 값
     * @return 찾은 아이템을 변환한 dto List
     */
    @Transactional(readOnly = true)
    public List<ItemApiDto> findDtypePriceRange(String dtype, int min, int max) {
        ItemCond itemCond = new ItemCond(min, max, dtype);
        List<Item> allItem = ItemApiRepository.findDtypePriceRange(itemCond);
        return allItem.stream()
                .map(item -> {
                    ItemApiDto itemApiDto = new ItemApiDto();
                    itemApiDto.setId(item.getId());
                    itemApiDto.setName(item.getName());
                    itemApiDto.setPrice(item.getPrice());
                    itemApiDto.setQuantity(item.getQuantity());
                    itemApiDto.setDtype(item.getDtype());
                    return itemApiDto;
                }).toList();
    }

    /**
     * 아이템을 업데이트 하기 위한 로직
     * 받아온 아이템을 아이템의 변경로직으로 변경 후 변경 감지로 업데이트
     * @param id 변경할 아이템의 id
     * @param request 아이템을 변경할 아이템 정보
     * @return 변경된 아이템 반환
     */
    public ItemApiDto updateItem(Long id, UpdateItemDto request) {
        Optional<Item> byId = ItemApiRepository.findById(id);


        Item item = byId.orElseThrow(() -> new IllegalArgumentException("not found:" + id));
        item.updateItem(request.getName(), request.getPrice(), request.getQuantity());
        ItemApiDto itemApiDto = new ItemApiDto();
        itemApiDto.setId(item.getId());
        itemApiDto.setName(item.getName());
        itemApiDto.setPrice(item.getPrice());
        itemApiDto.setQuantity(item.getQuantity());
        itemApiDto.setDtype(item.getDtype());

        return itemApiDto;

    }

    public void deleteByItemId(Long id) {
        ItemApiRepository.deleteById(id);
    }

    public void updateApiBook(Long id, UpdateBookDto request) {
        Book book = ItemApiRepository.updateBook(id);
        book.updateBook(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getIsbn(),
                request.getAuthor()
                );
    }
    @Transactional(readOnly = true)
    public BookApiDto findBook(Long id) {
        Book book = ItemApiRepository.findBook(id);
        return new BookApiDto(
                book.getId(),
                book.getName(),
                book.getPrice(),
                book.getQuantity(),
                book.getIsbn(),
                book.getAuthor()
        );
    }

    public void updateApiClothes(Long id, UpdateClothesDto request) {
        Clothes clothes = ItemApiRepository.updateClothes(id);
        clothes.updateClothes(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getClothesType(),
                request.getBrand(),
                request.getSize()
        );
    }
    @Transactional(readOnly = true)
    public ClothesApiDto findClothes(Long id) {
        Clothes clothes = ItemApiRepository.findClothes(id);
        return new ClothesApiDto(
                clothes.getId(),
                clothes.getName(),
                clothes.getPrice(),
                clothes.getQuantity(),
                clothes.getClothesType(),
                clothes.getBrand(),
                clothes.getSize()
        );
    }

    public void updateApiElectronics(Long id, UpdateElectronicsDto request) {
        Electronics electronics = ItemApiRepository.updateElectronics(id);
        electronics.updateElectronics(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
    }
    @Transactional(readOnly = true)
    public ElectronicsApiDto findElectronics(Long id) {
        Electronics electronics = ItemApiRepository.findElectronics(id);
        return new ElectronicsApiDto(
                electronics.getId(),
                electronics.getName(),
                electronics.getPrice(),
                electronics.getQuantity(),
                electronics.getBrand()
        );
    }

    public void updateApiFood(Long id, UpdateFoodDto request) {
        Food food = ItemApiRepository.updateFood(id);
        food.updateFood(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
    }
    @Transactional(readOnly = true)
    public FoodApiDto findFood(Long id) {
        Food food = ItemApiRepository.findFood(id);
        return new FoodApiDto(
                food.getId(),
                food.getName(),
                food.getPrice(),
                food.getQuantity(),
                food.getBrand()
        );
    }

    /**
     * book을 저장하기 위한 로직 dto로 저장할 book을 받아온다.
     * @param request 저장할 book의 정보가 담긴 dto
     * @return 저장 후 저장된 book을 반환
     */
    public BookApiDto saveBook(BookSaveApiDto request) {
        Book book = new Book(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getIsbn(),
                request.getAuthor()
        );
        Book saveBook = ItemApiRepository.save(book);
        return new BookApiDto(
                saveBook.getId(),
                saveBook.getName(),
                saveBook.getPrice(),
                saveBook.getQuantity(),
                saveBook.getIsbn(),
                saveBook.getAuthor()
        );
    }

    public ClothesApiDto saveClothes(ClothesSaveApiDto request) {
        Clothes clothes = new Clothes(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getClothesType(),
                request.getBrand(),
                request.getSize()
        );
        Clothes saveClothes = ItemApiRepository.save(clothes);
        return new ClothesApiDto(
                saveClothes.getId(),
                saveClothes.getName(),
                saveClothes.getPrice(),
                saveClothes.getQuantity(),
                saveClothes.getClothesType(),
                saveClothes.getBrand(),
                saveClothes.getSize()
        );
    }

    public ElectronicsApiDto saveElectronics(ElectronicsSaveApiDto request) {
        Electronics electronics = new Electronics(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
        Electronics saveElectronics = ItemApiRepository.save(electronics);
        return new ElectronicsApiDto(
                saveElectronics.getId(),
                saveElectronics.getName(),
                saveElectronics.getPrice(),
                saveElectronics.getQuantity(),
                saveElectronics.getBrand()
        );
    }

    public FoodApiDto saveFood(FoodSaveApiDto request) {
        Food food = new Food(
                request.getName(),
                request.getPrice(),
                request.getQuantity(),
                request.getBrand()
        );
        Food saveFood = ItemApiRepository.save(food);
        return new FoodApiDto(
                saveFood.getId(),
                saveFood.getName(),
                saveFood.getPrice(),
                saveFood.getQuantity(),
                saveFood.getBrand()
        );
    }

    @Transactional(readOnly = true)
    public Page<BookApiDto> findAllBookPaging(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return ItemApiRepository.findAllBookPaging(pageable);
    }

    @Transactional(readOnly = true)
    public Page<ElectronicsApiDto> findAllElectricPaging(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return ItemApiRepository.findAllElectronicsPaging(pageable);
    }
    @Transactional(readOnly = true)
    public Page<ClothesApiDto> findAllClothesPaging(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return ItemApiRepository.findAllClothesPaging(pageable);
    }
    @Transactional(readOnly = true)
    public Page<FoodApiDto> findAllFoodPaging(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return ItemApiRepository.findAllFoodPaging(pageable);
    }



    @Transactional(readOnly = true)
    public List<BookApiDto> findAllBookRe() {
        return ItemApiRepository.findAllBook();
    }
    @Transactional(readOnly = true)
    public List<BookApiDto> jpqlPaging(int offset,int limit) {
        return ItemApiRepository.jpqlPaging(offset,limit);
    }


    @Transactional(readOnly = true)
    public List<ItemApiDto> searchItem(String itemName) {
        return ItemApiRepository.itemNameSearch(itemName);
    }
}
