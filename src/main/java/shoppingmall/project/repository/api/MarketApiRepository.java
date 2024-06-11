package shoppingmall.project.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.Market;
import shoppingmall.project.repository.api.custom.MarketApiRepositoryCustom;

public interface MarketApiRepository extends JpaRepository<Market, Long>, MarketApiRepositoryCustom {
}
