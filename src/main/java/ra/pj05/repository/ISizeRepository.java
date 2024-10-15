package ra.pj05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.pj05.model.entity.Size;

public interface ISizeRepository extends JpaRepository<Size, Long> {
}
