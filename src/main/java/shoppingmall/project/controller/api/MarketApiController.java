package shoppingmall.project.controller.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import shoppingmall.project.apiresponse.CustomErrorResponse;
import shoppingmall.project.domain.dto.ItemDto;
import shoppingmall.project.service.api.MarketApiService;

import java.util.List;

@Tag(name = "Market", description = "Market API")
@RestController
@RequiredArgsConstructor
public class MarketApiController {

    private final MarketApiService marketApiService;


    @Operation(
            summary = "shopping Basket List",
            description = "파라미터에 지정한 유저의 장바구니 목록을 반환합니다."
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
            description = "존재하지 않는 user 입니다.")
    @GetMapping("/api/market/{userId}")
    public ResponseEntity<List<ItemDto>> purchaseTest(@PathVariable Long userId) {
        List<ItemDto> itemDtos = marketApiService.purchaseItem(userId);

        return ResponseEntity.ok().body(itemDtos);
    }

    @Operation(
            summary = "shopping Basket List Refactoring",
            description = "파라미터에 지정한 유저의 장바구니 목록을 리펙토링해서 반환합니다."
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
            description = "존재하지 않는 user 입니다.")
    @GetMapping("/api/market/refactor/{userId}")
    public ResponseEntity<List<ItemDto>> refactor(@PathVariable Long userId) {
        List<ItemDto> itemDtos = marketApiService.shoppingBasket(userId);

        return ResponseEntity.ok().body(itemDtos);
    }


}
