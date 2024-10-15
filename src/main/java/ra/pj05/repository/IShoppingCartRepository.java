package ra.pj05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.pj05.model.entity.ShoppingCart;
import ra.pj05.model.entity.Users;

public interface IShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUser(Users user);
}
