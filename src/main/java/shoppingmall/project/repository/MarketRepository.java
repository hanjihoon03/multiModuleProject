package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.Market;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.custom.MarketRepositoryCustom;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long>, MarketRepositoryCustom {
}
