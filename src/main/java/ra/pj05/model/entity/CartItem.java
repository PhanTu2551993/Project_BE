package ra.pj05.model.entity;

import jakarta.persistence.*;
import lombok.*;
import ra.pj05.model.entity.jointable.ProductSizeColor;

@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "product_size_color_id", nullable = false)
    private ProductSizeColor productSizeColor;
}
