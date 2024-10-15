package ra.pj05.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.pj05.exception.ChangeException;
import ra.pj05.model.dto.request.CartItemsRequest;
import ra.pj05.model.entity.*;
import ra.pj05.model.entity.jointable.ProductSizeColor;
import ra.pj05.repository.ICartItemRepository;
import ra.pj05.repository.IShoppingCartRepository;
import ra.pj05.service.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartItemServiceImpl implements ICartItemService {
    @Autowired
    private ICartItemRepository cartItemRepository;
    @Autowired
    private IProductSizeColorService productSizeColorService;
    @Autowired
    private IColorService colorService;
    @Autowired
    private ISizeService sizeService;
    @Autowired
    private IProductDetailService productDetailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IShoppingCartRepository shoppingCartRepository;
    @Override
    public CartItem addCartItem(CartItemsRequest cartItemsRequest) {
        Users currenUser = userService.getCurrentLoggedInUser();
        if (currenUser == null) {
            throw new ChangeException("Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng");
        }
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUser(currenUser);
        if (shoppingCart == null){
            shoppingCart = new ShoppingCart();
            shoppingCart.setUser(currenUser);
            shoppingCart.setCartItems(new ArrayList<>());
            shoppingCartRepository.save(shoppingCart);
        }
        ProductDetail productDetail = productDetailService.findProductDetailById(cartItemsRequest.getProductDetailId());
        Size size = sizeService.findSizeById(cartItemsRequest.getSizeId());
        Color color = colorService.findColorById(cartItemsRequest.getColorId());
        ProductSizeColor productSizeColor = productSizeColorService.findProductSizeColorById(productDetail.getProductDetailId(), size.getSizeId(), color.getColorId());
        CartItem existingCartItem = findByShoppingCartAndProductSizeColor(shoppingCart,productSizeColor);
        if(existingCartItem != null){
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemsRequest.getQuantity());
            cartItemRepository.save(existingCartItem);
            return existingCartItem;
        }
        CartItem newCartItem = new CartItem();
        newCartItem.setQuantity(cartItemsRequest.getQuantity());
        newCartItem.setProductSizeColor(productSizeColor);
        newCartItem.setShoppingCart(shoppingCart);
        shoppingCart.getCartItems().add(newCartItem);
        cartItemRepository.save(newCartItem);
        shoppingCart.setOrderQuantity(totalQuantity(shoppingCart));
        shoppingCartRepository.save(shoppingCart);
        return newCartItem;
    }

    @Override
    public CartItem findByShoppingCartAndProductSizeColor(ShoppingCart shoppingCart, ProductSizeColor productSizeColor) {
        return cartItemRepository.findByShoppingCartAndProductSizeColor(shoppingCart,productSizeColor);
    }

    public Integer totalQuantity(ShoppingCart shoppingCart){
        return  cartItemRepository.findByShoppingCart(shoppingCart)
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

    }

}
