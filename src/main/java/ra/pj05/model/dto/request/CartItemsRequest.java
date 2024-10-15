package ra.pj05.model.dto.request;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ra.pj05.model.entity.ShoppingCart;
import ra.pj05.model.entity.jointable.ProductSizeColor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemsRequest {
    private Integer quantity;
    private Long shoppingCartId;
    private Long productDetailId;
    private Long sizeId;
    private Long colorId;
}
