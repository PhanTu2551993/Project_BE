package ra.pj05.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.pj05.model.entity.Color;

public interface IColorRepository extends JpaRepository<Color,Long> {
}
