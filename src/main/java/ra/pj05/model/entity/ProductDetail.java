package ra.pj05.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ra.pj05.model.entity.jointable.ProductSizeColor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_detail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long productDetailId;
    private String image;
    private String productDetailName;
    private Boolean status;
    private Integer stock;
    private Double unitPrice;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productDetail")
    @JsonIgnore
    private List<ProductSizeColor> productSizeColors;
}
