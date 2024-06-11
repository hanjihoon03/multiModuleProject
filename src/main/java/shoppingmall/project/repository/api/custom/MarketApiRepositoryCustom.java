package shoppingmall.project.repository.api.custom;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.dto.ItemDto;

import java.util.List;

public interface MarketApiRepositoryCustom{
    List<ItemDto> findItemAndFile(List<Long> itemIds, Long userId);
    List<ItemDto> findItemAndFileRefactor(Long userId);
}
