package ra.pj05.service;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import ra.pj05.model.dto.request.ShoppingCartRequest;
import ra.pj05.model.entity.CartItem;
import ra.pj05.model.entity.ShoppingCart;
import ra.pj05.model.entity.Users;

import java.util.List;

public interface IShoppingCartService {
//    ShoppingCart addItemToCart(ShoppingCartRequest shoppingCartRequest, List<CartItem> cartItems);
    ShoppingCart findShoppingCartByUser(Users user);
}
