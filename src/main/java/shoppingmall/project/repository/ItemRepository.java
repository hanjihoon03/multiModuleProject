package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.item.Item;
import shoppingmall.project.repository.custom.ItemRepositoryCustom;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> , ItemRepositoryCustom{
}
