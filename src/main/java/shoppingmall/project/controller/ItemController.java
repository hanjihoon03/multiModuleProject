package shoppingmall.project.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import shoppingmall.project.service.FileService;
import shoppingmall.project.service.ItemService;
import shoppingmall.project.service.MarketService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final MarketService marketService;
    private final FileService fileService;

    @Value("${file.dir}")
    private String fileDir;


    /**
     * Book을 생성하기 위한 폼 이동
     * @param model 바인딩할 빈 객체
     * @return
     */
    @GetMapping("/admin/book")
    public String createBook(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "items/createItemBook";
    }

    /**
     * 새로 저장할 book을 받아 저장하는 컨트롤러
     * @param bookForm 전송 받은 Book Save
     * @return
     */
    @PostMapping("/admin/book")
    public String saveBook(@ModelAttribute BookForm bookForm){
        try {
            itemService.saveBook(bookForm);
            return "redirect:/adminPage";
        } catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }

    }

    @GetMapping("/admin/clothes")
    public String createClothes(Model model) {
        model.addAttribute("clothesForm", new ClothesForm());
        return "items/createItemClothes";
    }

    @PostMapping("/admin/clothes")
    public String saveClothes(@ModelAttribute ClothesForm clothesForm) {
        try {
        itemService.saveClothes(clothesForm);
        return "redirect:/adminPage";
        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    @GetMapping("/admin/food")
    public String createFood(Model model) {
        model.addAttribute("foodForm", new FoodForm());
        return "items/createItemFood";
    }

    @PostMapping("/admin/food")
    public String saveFood(@ModelAttribute FoodForm foodForm){
        try {
        itemService.saveFood(foodForm);
            return "redirect:/adminPage";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    @GetMapping("/admin/electronics")
    public String createElectronics(Model model) {
        model.addAttribute("electronicsForm", new ElectronicsForm());
        return "items/createItemElectronics";
    }

    @PostMapping("/admin/electronics")
    public String saveElectronics(@ModelAttribute ElectronicsForm electronicsForm){
        try {
        itemService.saveElectronics(electronicsForm);
            return "redirect:/adminPage";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    /**
     * book 조회를 위한 컨트롤러
     * @param model
     * @param page 하나의 페이지에 30개의 데이터를 보여주도록 설정했으며 페이지 default = 0 페이지 넘어갈수록 url 파라미터로 페이지 번호를 받음
     * @return
     */

    @GetMapping("/bookList")
    public String bookList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<BookAndFileDto> allBook = itemService.findAllBook(page);

        model.addAttribute("allBook", allBook);

        return "list/bookList";
    }
    @GetMapping("/clothesList")
    public String clothesList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        Page<ClothesAndFileDto> allClothes = itemService.findAllClothes(page);

        model.addAttribute("allClothes", allClothes);

        return "list/clothesList";
    }
    @GetMapping("/electronicsList")
    public String electronicsList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<ElectronicsAndFileDto> allElectronics = itemService.findAllElectronics(page);

        model.addAttribute("allElectronics", allElectronics);

        return "list/electronicsList";
    }
    @GetMapping("/foodList")
    public String foodList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<FoodAndFileDto> allFood = itemService.findAllFood(page);

        model.addAttribute("allFood", allFood);

        return "list/foodList";
    }

    /**
     * 장바구니에 user가 원하는 item을 담는 컨트롤러
     * @param itemId 선택한 아이템
     * @param quantity 구매하는 아이템의 개수
     * @param session 구매하는 user의 세션정보
     * @return
     */
    @PostMapping("/addCart")
    public String buyItem(@RequestParam("itemId") Long itemId,
                        @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                          HttpSession session) {

        // 장바구니추가시마다 정보 누적 생성
        Item cartAddItem = itemService.findById(itemId);

        if (quantity > cartAddItem.getQuantity()) {
            return "error/cartError";
        }


        marketService.addToCart(itemId, quantity, session, cartAddItem);

        return "redirect:";


    }

    /**
     * 아이템을 수정하기 위한 컨트롤러 기존 아이템의 리스트들을 보여주는 로직과 다를 건 없다.
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/admin/modifyBook")
    public String itemBookList(Model model,@RequestParam(value = "page", defaultValue = "0") int page) {
        //수정을 위한 현재 아이템 표시
        Page<BookAndFileDto> allBook = itemService.findAllBook(page);

        model.addAttribute("allBook", allBook);

        return "admin/modifyBook";
    }

    /**
     * 아이텡믈 수정하기 위한 컨트롤러 수정할 아이템을 선택후 수정 폼으로 이동하는 컨트롤러 수정할 아이템 정보가 폼에 입력되어 있게 함.
     * 리팩토링 가능점은 book과 file을 한번에 받아 오도록 할 수 있고 서비스 로직에 기능을 몰아 넣을 수 있다.
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping("/admin/modifyBook/{itemId}/edit")
    public String updateItemBook(@PathVariable("itemId") Long itemId, Model model) {
        Book oneBook = itemService.findOneBook(itemId);
        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);

        BookAndFileDto bookAndFileDto = new BookAndFileDto(
                oneBook.getId(),
                oneBook.getIsbn(),
                oneBook.getAuthor(),
                oneBook.getName(),
                oneBook.getPrice(),
                oneBook.getQuantity(),
                oneUploadFile.getUploadFileName(),
                oneUploadFile.getStoreFileName()
        );
        model.addAttribute("bookAndFileDto", bookAndFileDto);


        return "admin/updateItemBook";

    }

    /**
     * 수정 폼에서 아이템의 성보를 수정해 bookform을 받고 파라미터로 id를 받아 수정하는 컨트롤러
     * @param itemId
     * @param bookForm
     * @return
     */
    @PostMapping("/admin/modifyBook/{itemId}/edit")
    public String updateBook(@PathVariable Long itemId, @ModelAttribute("form") BookForm bookForm){
        try {
        itemService.updateBook(itemId, bookForm);
        return "redirect:/admin/modifyBook";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }

    @GetMapping("/admin/modifyClothes")
    public String itemClothesList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        //수정을 위한 현재 아이템 표시
        Page<ClothesAndFileDto> allClothes = itemService.findAllClothes(page);



        model.addAttribute("allClothes", allClothes);

        return "admin/modifyClothes";
    }

    @GetMapping("/admin/modifyClothes/{itemId}/edit")
    public String updateItemClothes(@PathVariable("itemId") Long itemId, Model model) {

        Clothes oneClothes = itemService.findOneClothes(itemId);
        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);
        //객체 생성 서비스로 빼기
        ClothesAndFileDto clothesAndFileDto = new ClothesAndFileDto(
                oneClothes.getId(),
                oneClothes.getName(),
                oneClothes.getPrice(),
                oneClothes.getQuantity(),
                oneUploadFile.getStoreFileName(),
                oneUploadFile.getUploadFileName(),
                oneClothes.getClothesType(),
                oneClothes.getBrand(),
                oneClothes.getSize()
        );
        model.addAttribute("clothesAndFileDto", clothesAndFileDto);


        return "admin/updateItemClothes";

    }


    @PostMapping("/admin/modifyClothes/{itemId}/edit")
    public String updateClothes(@PathVariable Long itemId, @ModelAttribute("form") ClothesForm clothesForm){
        try {
        itemService.updateClothes(itemId, clothesForm);
        return "redirect:/admin/modifyClothes";
        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }


    @GetMapping("/admin/modifyElectronics")
    public String itemElectronicsList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        //수정을 위한 현재 아이템 표시
        Page<ElectronicsAndFileDto> allElectronics = itemService.findAllElectronics(page);

        model.addAttribute("allElectronics", allElectronics);
        return "admin/modifyElectronics";
    }


    @GetMapping("/admin/modifyElectronics/{itemId}/edit")
    public String updateItemElectronics(@PathVariable("itemId") Long itemId, Model model) {
        Electronics oneElectronics = itemService.findOneElectronics(itemId);

        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);

        ElectronicsAndFileDto electronicsAndFileDto = new ElectronicsAndFileDto(
                oneElectronics.getId(),
                oneElectronics.getName(),
                oneElectronics.getPrice(),
                oneElectronics.getQuantity(),
                oneElectronics.getBrand(),
                oneUploadFile.getUploadFileName(),
                oneUploadFile.getStoreFileName()
        );
        model.addAttribute("electronicsAndFileDto", electronicsAndFileDto);


        return "admin/updateItemElectronics";

    }


    @PostMapping("/admin/modifyElectronics/{itemId}/edit")
    public String updateElectronics(@PathVariable Long itemId, @ModelAttribute("form") ElectronicsForm electronicsForm){
        try {
        itemService.updateElectronics(itemId,electronicsForm);
        return "redirect:/admin/modifyElectronics";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }


    @GetMapping("/admin/modifyFood")
    public String itemFoodList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        //수정을 위한 현재 아이템 표시
        Page<FoodAndFileDto> allFood = itemService.findAllFood(page);

        model.addAttribute("allFood", allFood);

        return "admin/modifyFood";
    }


    @GetMapping("/admin/modifyFood/{itemId}/edit")
    public String updateItemFood(@PathVariable("itemId") Long itemId, Model model) {
        Food oneFood = itemService.findOneFood(itemId);
        UploadFile oneUploadFile = fileService.findUploadFileItemId(itemId);

        FoodAndFileDto foodAndFileDto = new FoodAndFileDto(
                oneFood.getId(),
                oneFood.getName(),
                oneFood.getPrice(),
                oneFood.getQuantity(),
                oneFood.getBrand(),
                oneUploadFile.getUploadFileName(),
                oneUploadFile.getStoreFileName()
        );

        model.addAttribute("foodAndFileDto", foodAndFileDto);

        return "admin/updateItemFood";

    }


    @PostMapping("/admin/modifyFood/{itemId}/edit")
    public String updateFood(@PathVariable Long itemId, @ModelAttribute("form") FoodForm foodForm){
        try {
        itemService.updateFood(itemId,foodForm);
        return "redirect:/admin/modifyFood";

        }catch (IOException ex) {
            log.error("IOException = {}", ex.getMessage());
            return "redirect:/ioError";
        }
    }
    @PostMapping("/deleteItem/{itemId}")
    public String deleteItem(@PathVariable Long itemId){
        log.info("id=================={}", itemId);
        itemService.deleteFileByItemId(itemId);
        itemService.deleteItemByItemId(itemId);
        return "redirect:/adminPage";
    }




    @GetMapping("uploadFileError")
    public String uploadFileError() {
        return "error/uploadFileError";
    }

    /**
     * 아이템을 저장 할 떄 이미지 파일이 없다면 nullpointexception이 발생하고 에러페이지 출력
     */
    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointException(NullPointerException ex) {
        log.error("Exception ={}", ex.getMessage());
        return "redirect:/uploadFileError";
    }

    /**
     *ioException 발생시 이동
     */
    @GetMapping("ioError")
    public String ioError() {
        return "error/ioError";
    }

    /**
     * 화면에 이미지를 보여주기 위한 컨트롤러 각 이미지 filename에 맞는 이미지를 저장소에서 출력
     * @param filename
     * @return
     * @throws MalformedURLException
     */
    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        // 파일 경로를 이용하여 실제 파일의 위치를 확인
        String fullPath = fileService.getFullPath(filename);
        Path filePath = Paths.get(fullPath);

        // 파일이 존재하고, 허용된 디렉토리에 있는지 확인
        if (Files.exists(filePath) && filePath.startsWith(Paths.get(fileDir))) {
            // UrlResource를 사용하여 리소스를 반환
            Resource resource = new UrlResource(filePath.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // 이미지인 경우에는 적절한 미디어 타입을 설정
                    .body(resource).getBody();
        } else {
            // 파일이 존재하지 않거나 허용되지 않은 디렉토리에 있는 경우 404 에러 반환
            return (Resource) ResponseEntity.notFound().build();
        }
    }


}
