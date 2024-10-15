package ra.pj05.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ra.pj05.model.entity.jointable.ProductSizeColor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "size")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sizeId;

    @Column(nullable = false)
    private String sizeName;

    @OneToMany(mappedBy = "size")
    @JsonIgnore
    private List<ProductSizeColor> productSizeColors;

}
