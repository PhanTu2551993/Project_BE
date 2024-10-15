package ra.pj05.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ra.pj05.model.dto.request.CartItemsRequest;
import ra.pj05.model.entity.CartItem;
import ra.pj05.service.ICartItemService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private ICartItemService cartItemService;

    @PostMapping("/shoppingCart/cartItem")
    public ResponseEntity<CartItem> addCartItemToShoppingCart(@RequestBody CartItemsRequest cartItemsRequest) {
        CartItem cartItem = cartItemService.addCartItem(cartItemsRequest);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
}
