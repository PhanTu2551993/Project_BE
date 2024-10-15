package ra.pj05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.pj05.model.entity.Product;
import ra.pj05.model.entity.ProductDetail;

import java.util.List;

@Repository
public interface IProductDetailRepository extends JpaRepository<ProductDetail,Long> {
    Page<ProductDetail> findByProductDetailNameContaining(String productDetailName, Pageable pageable);
    List<ProductDetail> findByProduct(Product product);

}
