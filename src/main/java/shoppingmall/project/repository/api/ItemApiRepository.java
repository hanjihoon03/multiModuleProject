package shoppingmall.project.repository.api;

import org.springframework.data.jpa.repository.JpaRepository;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.api.custom.ItemApiRepositoryCustom;

import java.util.List;

public interface ItemApiRepository extends JpaRepository<Item, Long>, ItemApiRepositoryCustom {
    List<Item> findByDtype(String dtype);
}
