package ra.pj05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.pj05.model.entity.Category;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByCategoryNameContainingOrDescriptionContaining(String categoryName, String description, Pageable pageable);
}
