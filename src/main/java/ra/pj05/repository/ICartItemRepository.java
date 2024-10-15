package ra.pj05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.pj05.model.entity.CartItem;
import ra.pj05.model.entity.ShoppingCart;
import ra.pj05.model.entity.jointable.ProductSizeColor;

import java.util.List;

public interface ICartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByShoppingCartAndProductSizeColor(ShoppingCart shoppingCart, ProductSizeColor productSizeColor);
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
