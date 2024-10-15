package ra.pj05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.pj05.model.entity.Category;
import ra.pj05.model.entity.Product;

import java.util.List;

public interface IProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findByProductNameContainingOrSkuContainingOrDescriptionContaining(String productName, String sku, String description, Pageable pageable);
    List<Product> findByCategory(Category category);
}
