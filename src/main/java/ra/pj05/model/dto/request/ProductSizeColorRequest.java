package ra.pj05.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductSizeColorRequest {
    private String colorName;
    private Long productDetail;
    private String sizeName;
    private Integer quantity;
    private Double price;
    private MultipartFile image;
    private String description;
    private Date creationDate;
    private Date modificationDate;
}
