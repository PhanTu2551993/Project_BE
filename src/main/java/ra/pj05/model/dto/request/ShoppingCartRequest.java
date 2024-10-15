package ra.pj05.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.pj05.model.entity.CartItem;
import ra.pj05.model.entity.Users;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShoppingCartRequest {
    private Integer orderQuantity;
    private Long userId;
    private List<CartItem> cartItems;
}
