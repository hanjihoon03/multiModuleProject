package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.Delivery;
import shoppingmall.project.repository.custom.DeliveryRepositoryCustom;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryRepositoryCustom {
}
