package ra.pj05.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import ra.pj05.model.entity.jointable.ProductSizeColor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "color")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long colorId;

    @Column(nullable = false)
    private String colorName;


    @OneToMany(mappedBy = "color")
    @JsonIgnore
    private List<ProductSizeColor> productSizeColors;
}
