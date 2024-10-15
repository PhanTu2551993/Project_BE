package ra.pj05.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.pj05.model.entity.Roles;
import ra.pj05.model.entity.Users;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUsersByUsername(String username);
    Page<Users> findByUsernameContainingOrPhoneContainingOrEmailContainingOrFullNameContaining(String username, String phone, String email, String fullName, Pageable pageable);
}
