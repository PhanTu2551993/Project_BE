package ra.pj05.service;

import ra.pj05.model.dto.request.CartItemsRequest;
import ra.pj05.model.entity.CartItem;
import ra.pj05.model.entity.ShoppingCart;
import ra.pj05.model.entity.jointable.ProductSizeColor;

import java.util.List;

public interface ICartItemService {
    CartItem addCartItem(CartItemsRequest cartItemsRequest);
    CartItem findByShoppingCartAndProductSizeColor(ShoppingCart shoppingCart, ProductSizeColor productSizeColor);
}
