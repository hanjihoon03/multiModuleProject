package shoppingmall.project.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shoppingmall.project.apiresponse.CustomErrorResponse;
import shoppingmall.project.domain.apidto.*;
import shoppingmall.project.domain.apidto.save.BookSaveApiDto;
import shoppingmall.project.domain.apidto.save.ClothesSaveApiDto;
import shoppingmall.project.domain.apidto.save.ElectronicsSaveApiDto;
import shoppingmall.project.domain.apidto.save.FoodSaveApiDto;
import shoppingmall.project.domain.apidto.update.*;
import shoppingmall.project.domain.dto.BookAndFileDto;
import shoppingmall.project.service.api.ItemApiService;
import shoppingmall.project.service.api.UploadApiService;

import java.util.Arrays;
import java.util.List;

@Tag(name = "Item", description = "Item API")
@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemApiService itemApiService;
    private final UploadApiService uploadApiService;

    @Operation(
            summary = "Item",
            description = "모든 아이템을 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/items")
    public List<ItemApiDto> allItemSearch() {
        return itemApiService.findAllItem();
    }

    @Operation(
            summary = "Item type",
            description = "파라미터에 지정한 아이템을 반환합니다." +
                        "아이템 목록: Book, Food, Electronics, Clothes"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "잘못된 dtype을 지정했습니다.")
    @GetMapping("/api/item/{dtype}")
    public ResponseEntity<List<ItemApiDto>> allDtypeSearch(@PathVariable String dtype) {
        List<ItemApiDto> allDtype = itemApiService.findAllDtype(dtype);
        if (!Arrays.asList("Book", "Food", "Electronics", "Clothes").contains(dtype)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .body(allDtype);
    }
    @Operation(
            summary = "Item Price Range",
            description = "원하는 가격 사이 Item을 반환. ex)api/item/condition?min=10&max=20"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/item/condition")
    public ResponseEntity<List<ItemApiDto>> getItemByRange(@RequestParam("min") int min, @RequestParam("max") int max) {
        List<ItemApiDto> rangePriceItem = itemApiService.findPriceRange(min, max);

        return ResponseEntity.ok()
                .body(rangePriceItem);
    }

    @Operation(
            summary = "Item type Price Range",
            description = "원하는 가격 사이의 원하는 Item을 반환. ex)api/item/condition?min=10&max=20/Book"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "잘못된 dtype을 지정했습니다.")
    @GetMapping("/api/item/condition/{dtype}")
    public ResponseEntity<List<ItemApiDto>> getItemDtypeByRange(@PathVariable String dtype, @RequestParam("min") int min, @RequestParam("max") int max) {
        List<ItemApiDto> rangePriceItem = itemApiService.findDtypePriceRange(dtype, min, max);

        if (!Arrays.asList("Book", "Food", "Electronics", "Clothes").contains(dtype)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .body(rangePriceItem);
    }

    @Operation(
            summary = "Item update",
            description = "원하는 Item을 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @PostMapping("/api/item/{id}")
    public ResponseEntity<ItemApiDto> updateItem(@PathVariable Long id,
                                                 @RequestBody UpdateItemDto request) {
        ItemApiDto itemApiDto = itemApiService.updateItem(id, request);
        return ResponseEntity.ok()
                .body(itemApiDto);
    }
    @Operation(
            summary = "Item delete",
            description = "원하는 Item을 삭제"
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )

    @DeleteMapping("/api/item/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        uploadApiService.deleteUploadFileFromItemId(id);
        itemApiService.deleteByItemId(id);

        return ResponseEntity.ok()
                .build();
    }

    @Operation(
            summary = "Book update",
            description = "수정할 Book과 Book의 id를 입력해주세요."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Book이 업데이트되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "올바르지 않은 요청 값입니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/Book/{dtype}/{id}")
    public ResponseEntity<BookApiDto> updateBook(@PathVariable String dtype, @PathVariable Long id,
                                                 @RequestBody UpdateBookDto request) {
        if (!dtype.equals("Book")) {
            return ResponseEntity.badRequest().build();
        }
        itemApiService.updateApiBook(id, request);
        BookApiDto bookApiDto = itemApiService.findBook(id);

        return ResponseEntity.ok()
                .body(bookApiDto);
    }


    @Operation(
            summary = "Clothes update",
            description = "수정할 Clothes와 Clothes id를 입력해주세요. 수정시  clothesType은  PANTS,SHIRTS 둘 중 하나를 선택해야 합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Clothes 업데이트되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClothesApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "올바르지 않은 요청 값입니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/Clothes/{dtype}/{id}")
    public ResponseEntity<ClothesApiDto> updateClothes(@PathVariable String dtype, @PathVariable Long id,
                                                 @RequestBody UpdateClothesDto request) {
        if (!dtype.equals("Clothes")) {
            return ResponseEntity.badRequest().build();
        }
        itemApiService.updateApiClothes(id,request);
        ClothesApiDto clothesApiDto = itemApiService.findClothes(id);

        return ResponseEntity.ok()
                .body(clothesApiDto);
    }

    @Operation(
            summary = "Electronics update",
            description = "수정할 Electronics와 Electronics id를 입력해주세요."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Electronics 업데이트되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectronicsApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "올바르지 않은 요청 값입니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/Electronics/{dtype}/{id}")
    public ResponseEntity<ElectronicsApiDto> updateElectronics(@PathVariable String dtype, @PathVariable Long id,
                                                       @RequestBody UpdateElectronicsDto request) {
        if (!dtype.equals("Electronics")) {
            return ResponseEntity.badRequest().build();
        }
        itemApiService.updateApiElectronics(id,request);
        ElectronicsApiDto electronicsApiDto = itemApiService.findElectronics(id);

        return ResponseEntity.ok()
                .body(electronicsApiDto);
    }

    @Operation(
            summary = "Food update",
            description = "수정할 Food와 Food id를 입력해주세요."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Food 업데이트되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "올바르지 않은 요청 값입니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/Food/{dtype}/{id}")
    public ResponseEntity<FoodApiDto> updateFood(@PathVariable String dtype, @PathVariable Long id,
                                                               @RequestBody UpdateFoodDto request) {
        if (!dtype.equals("Food")) {
            return ResponseEntity.badRequest().build();
        }
        itemApiService.updateApiFood(id,request);
        FoodApiDto foodApiDto = itemApiService.findFood(id);

        return ResponseEntity.ok()
                .body(foodApiDto);
    }

    @Operation(
            summary = "Save Book",
            description = "Book을 저장하는 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Book이 저장되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookSaveApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버의 상태가 올바르지 않습니다",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/item/Book")
    public ResponseEntity<BookApiDto> saveApiBook(@RequestBody BookSaveApiDto request) {
        BookApiDto bookApiDto = itemApiService.saveBook(request);

        return ResponseEntity.ok()
                .body(bookApiDto);
    }

    @Operation(
            summary = "Save Clothes",
            description = "Clothes를 저장하는 API, clothesType은  PANTS,SHIRTS 둘 중 하나를 선택해야 합니다. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Clothes가 저장되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClothesSaveApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버의 상태가 올바르지 않습니다",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/item/Clothes")
    public ResponseEntity<ClothesApiDto> saveApiClothes(@RequestBody ClothesSaveApiDto request) {
        ClothesApiDto clothesApiDto = itemApiService.saveClothes(request);

        return ResponseEntity.ok()
                .body(clothesApiDto);
    }

    @Operation(
            summary = "Save Electronics",
            description = "Electronics를 저장하는 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Electronics가 저장되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ElectronicsSaveApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버의 상태가 올바르지 않습니다",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/item/Electronics")
    public ResponseEntity<ElectronicsApiDto> saveApiElectronics(@RequestBody ElectronicsSaveApiDto request) {
        ElectronicsApiDto electronicsApiDto = itemApiService.saveElectronics(request);

        return ResponseEntity.ok()
                .body(electronicsApiDto);
    }


    @Operation(
            summary = "Save Food",
            description = "Food를 저장하는 API"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "성공적으로 Food가 저장되었습니다.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = FoodSaveApiDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버의 상태가 올바르지 않습니다",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
            )
    })
    @PostMapping("/api/item/Food")
    public ResponseEntity<FoodApiDto> saveApiFood(@RequestBody FoodSaveApiDto request) {
        FoodApiDto foodApiDto = itemApiService.saveFood(request);

        return ResponseEntity.ok()
                .body(foodApiDto);
    }

    @Operation(
            summary = "PageBook",
            description = "Book을 paging해서 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/bookList")
    public ResponseEntity<Page<BookApiDto>> bookList(@RequestParam(value = "page", defaultValue = "0") int page) {

        Page<BookApiDto> allBook = itemApiService.findAllBookPaging(page);

        return ResponseEntity.ok()
                .body(allBook);
    }

    @Operation(
            summary = "PageClothes",
            description = "Clothes paging해서 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/clothesList")
    public ResponseEntity<Page<ClothesApiDto>> clothesList(@RequestParam(value = "page", defaultValue = "0") int page) {

        Page<ClothesApiDto> allClothesPaging = itemApiService.findAllClothesPaging(page);

        return ResponseEntity.ok()
                .body(allClothesPaging);
    }

    @Operation(
            summary = "PageFood",
            description = "Food을 paging해서 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/foodList")
    public ResponseEntity<Page<FoodApiDto>> foodList(@RequestParam(value = "page", defaultValue = "0") int page) {

        Page<FoodApiDto> allFoodPaging = itemApiService.findAllFoodPaging(page);

        return ResponseEntity.ok()
                .body(allFoodPaging);
    }


    @Operation(
            summary = "PageElectronics",
            description = "Electronics를 paging해서 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/electronicsList")
    public ResponseEntity<Page<ElectronicsApiDto>> electronicsList(@RequestParam(value = "page", defaultValue = "0") int page) {

        Page<ElectronicsApiDto> allElectricPaging = itemApiService.findAllElectricPaging(page);

        return ResponseEntity.ok()
                .body(allElectricPaging);
    }



    @Operation(
            summary = "allBook",
            description = "모든 Book의 자세한 정보를 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/allBook")
    public ResponseEntity<List<BookApiDto>> allBook() {
        List<BookApiDto> allBookRe = itemApiService.findAllBookRe();
        return ResponseEntity.ok()
                .body(allBookRe);
    }

    @Operation(
            summary = "PageBookJPQL",
            description = "Book을 jpql로 paging해서 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/Book/Jpql")
    public ResponseEntity<List<BookApiDto>> bookListJpql(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                         @RequestParam(value = "limit") int limit) {

        List<BookApiDto> bookApiDtos = itemApiService.jpqlPaging(offset,limit);

        return ResponseEntity.ok()
                .body(bookApiDtos);
    }


    @Operation(
            summary = "Search ItemName",
            description = "검색하는 ItemName을 포함한 Item을 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/searchItem/{itemName}")
    public ResponseEntity<List<ItemApiDto>> searchItem(@PathVariable String itemName) {

        List<ItemApiDto> searchItem = itemApiService.searchItem(itemName);

        return ResponseEntity.ok()
                .body(searchItem);
    }



}
