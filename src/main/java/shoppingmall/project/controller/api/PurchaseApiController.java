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
import shoppingmall.project.domain.apidto.UserPurchaseDto;
import shoppingmall.project.service.api.PurchaseApiService;

import java.util.List;

@Tag(name = "Purchase", description = "Purchase API")
@RestController
@RequiredArgsConstructor
public class PurchaseApiController {

    private final PurchaseApiService purchaseApiService;


    @Operation(
            summary = "Purchase",
            description = "지정한 id의 user가 구매한 목록을 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "OK")
    @ApiResponse(
            responseCode = "400",
            description = "올바르지 않은 요청 값입니다.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomErrorResponse.class))
    )
    @GetMapping("/api/purchase/{id}")
    public ResponseEntity<List<UserPurchaseDto>> userPurchase(@PathVariable Long id) {
        List<UserPurchaseDto> userPurchaseDtos = purchaseApiService.purchaseList(id);

        return ResponseEntity.ok()
                .body(userPurchaseDtos);
    }



}
