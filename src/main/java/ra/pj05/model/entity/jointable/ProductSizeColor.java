package ra.pj05.model.entity.jointable;

import jakarta.persistence.*;
import lombok.*;
import ra.pj05.model.entity.Color;
import ra.pj05.model.entity.ProductDetail;
import ra.pj05.model.entity.Size;

import java.util.Date;

@Entity
@Table(name = "product_size_color")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductSizeColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_size_color_id")
    private Long productSizeColorId;

    @ManyToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;
    private Integer quantity;
    private Double price;
    private String image;
    private String description;
    private Date creationDate;
    private Date modificationDate;
}
