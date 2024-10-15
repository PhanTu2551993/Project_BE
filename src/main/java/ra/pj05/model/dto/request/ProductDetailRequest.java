package ra.pj05.model.dto.request;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ra.pj05.model.entity.Product;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetailRequest {
    private MultipartFile image;
    private String productDetailName;
    private Boolean status;
    private Integer stock;
    private Double unitPrice;
    private Long productId;
}
