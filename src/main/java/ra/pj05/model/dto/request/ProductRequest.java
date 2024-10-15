package ra.pj05.model.dto.request;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.web.multipart.MultipartFile;
import ra.pj05.model.entity.Brand;
import ra.pj05.model.entity.Category;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {
    private String productName;
    private String description;
    private MultipartFile image;
    private Long categoryId;
    private Long brandId;
    private Date updatedAt;
}
