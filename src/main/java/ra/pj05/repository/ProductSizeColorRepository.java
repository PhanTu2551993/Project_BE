package ra.pj05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.pj05.model.entity.Color;
import ra.pj05.model.entity.ProductDetail;
import ra.pj05.model.entity.Size;
import ra.pj05.model.entity.jointable.ProductSizeColor;

import java.util.List;
import java.util.Optional;

public interface ProductSizeColorRepository extends JpaRepository<ProductSizeColor, Long> {
    List<ProductSizeColor> findByProductDetail (ProductDetail productDetail);
    Optional<ProductSizeColor> findByProductDetailAndSizeAndColor(ProductDetail productDetail, Size size, Color color);
}
