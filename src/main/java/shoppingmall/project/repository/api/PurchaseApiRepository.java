package shoppingmall.project.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.Purchase;
import shoppingmall.project.repository.api.custom.PurchaseApiRepositoryCustom;

public interface PurchaseApiRepository extends JpaRepository<Purchase, Long>, PurchaseApiRepositoryCustom {
}
