package shoppingmall.project.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.project.domain.UploadFile;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.domain.dto.ClothesAndFileDto;
import shoppingmall.project.domain.dto.ElectronicsAndFileDto;
import shoppingmall.project.domain.dto.FoodAndFileDto;
import shoppingmall.project.domain.item.*;

import shoppingmall.project.form.itemform.BookForm;
import shoppingmall.project.form.itemform.ClothesForm;
import shoppingmall.project.form.itemform.ElectronicsForm;
import shoppingmall.project.form.itemform.FoodForm;
import shoppingmall.project.repository.FileRepository;
import shoppingmall.project.repository.ItemRepository;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;



    public void saveItem(Item item){
        itemRepository.save(item);
    }
    @Transactional(readOnly = true)
    public Item findById(Long id) {
        Optional<Item> byId = itemRepository.findById(id);
        return byId.orElse(null);
    }

    //아이템보다 파일을 먼저 저장해서 파일의 아이템 아이디가 비어서 업데이트 쿼리가 한 방 더 나가는 문제를 아이템을 먼저 저장해서 해결

    /**
     * book을 저장하는 로직
     * @param bookForm 저장할 book의 정보와 이미지를 받아 저장
     * @throws IOException 이미지가 누락될 경우 IOException 발생
     */
    public void saveBook(BookForm bookForm) throws IOException {

            UploadFile attachFile = fileService.storeFile(bookForm.getAttachFile());

            Item book = new Book(bookForm.getName(),
                    bookForm.getPrice(),
                    bookForm.getQuantity(),
                    bookForm.getIsbn(),
                    bookForm.getAuthor());
            UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), book);
            itemRepository.save(book);
            fileRepository.save(uploadFile);
    }

    public void saveClothes(ClothesForm clothesForm) throws IOException {
        UploadFile attachFile = fileService.storeFile(clothesForm.getAttachFile());
        Item clothes = new Clothes(
                clothesForm.getName(),
                clothesForm.getPrice(),
                clothesForm.getQuantity(),
                clothesForm.getClothesType(),
                clothesForm.getBrand(),
                clothesForm.getSize());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), clothes);

        itemRepository.save(clothes);
        fileRepository.save(uploadFile);
    }

    public void saveFood(FoodForm foodForm) throws IOException {
        UploadFile attachFile = fileService.storeFile(foodForm.getAttachFile());
        Item food = new Food(
                foodForm.getName(),
                foodForm.getPrice(),
                foodForm.getQuantity(),
                foodForm.getBrand());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), food);
        itemRepository.save(food);
        fileRepository.save(uploadFile);
    }

    public void saveElectronics(ElectronicsForm electronicsForm) throws IOException {
        UploadFile attachFile = fileService.storeFile(electronicsForm.getAttachFile());
        Item electronics = new Electronics(
                electronicsForm.getName(),
                electronicsForm.getPrice(),
                electronicsForm.getQuantity(),
                electronicsForm.getBrand());
        UploadFile uploadFile = new UploadFile(attachFile.getUploadFileName(), attachFile.getStoreFileName(), electronics);
        itemRepository.save(electronics);
        fileRepository.save(uploadFile);
    }

    @Transactional(readOnly = true)
    public Page<BookAndFileDto> findAllBook(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return itemRepository.pagingBook(pageable);

    }
    @Transactional(readOnly = true)
    public Book findOneBook(Long id) {
        return itemRepository.findBook(id);
    }
    @Transactional(readOnly = true)
    public Page<ClothesAndFileDto> findAllClothes(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return itemRepository.pagingClothes(pageable);
    }
    @Transactional(readOnly = true)
    public Clothes findOneClothes(Long id) {
        return itemRepository.findClothes(id);
    }
    @Transactional(readOnly = true)
    public Page<ElectronicsAndFileDto> findAllElectronics(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return itemRepository.pagingElectronics(pageable);
    }
    @Transactional(readOnly = true)
    public Electronics findOneElectronics(Long id) {
        return itemRepository.findElectronics(id);
    }
    @Transactional(readOnly = true)
    public Page<FoodAndFileDto> findAllFood(int page) {
        Pageable pageable = PageRequest.of(page,30);
        return itemRepository.pagingFood(pageable);
    }
    @Transactional(readOnly = true)
    public Food findOneFood(Long id) {
        return itemRepository.findFood(id);
    }



    public void updateBook(Long bookId, BookForm bookForm) throws IOException {
        Book book = itemRepository.findBook(bookId);
        book.updateBook(
                bookForm.getName(),
                bookForm.getPrice(),
                bookForm.getQuantity(),
                bookForm.getIsbn(),
                bookForm.getAuthor());

        UploadFile attachFile = fileService.storeFile(bookForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(bookId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }
    public void updateClothes(Long clothesId, ClothesForm clothesForm) throws IOException {
        Clothes clothes = itemRepository.findClothes(clothesId);
        clothes.updateClothes(
                clothesForm.getName(),
                clothesForm.getPrice(),
                clothesForm.getQuantity(),
                clothesForm.getClothesType(),
                clothesForm.getBrand(),
                clothesForm.getSize()
        );
        UploadFile attachFile = fileService.storeFile(clothesForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(clothesId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }

    public void updateElectronics(Long electronicsId, ElectronicsForm electronicsForm) throws IOException {
        Electronics electronics = itemRepository.findElectronics(electronicsId);
        electronics.updateElectronics(
                electronicsForm.getName(),
                electronicsForm.getPrice(),
                electronicsForm.getQuantity(),
                electronicsForm.getBrand()
        );

        UploadFile attachFile = fileService.storeFile(electronicsForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(electronicsId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }
    public void updateFood(Long foodId, FoodForm foodForm) throws IOException {

        Food food = itemRepository.findFood(foodId);
        food.updateFood(
                foodForm.getName(),
                foodForm.getPrice(),
                foodForm.getQuantity(),
                foodForm.getBrand()
        );
        UploadFile attachFile = fileService.storeFile(foodForm.getAttachFile());
        UploadFile uploadFile = fileRepository.findByItemId(foodId);

        uploadFile.updateUploadFile(
                attachFile.getUploadFileName(),
                attachFile.getStoreFileName());
    }




    public void clear() {
        itemRepository.deleteAll();
    }
    public void deleteItemByItemId(Long id) {
        itemRepository.deleteById(id);
    }
    public void deleteFileByItemId(Long id) {
        fileRepository.deleteFromItemId(id);
    }


}
