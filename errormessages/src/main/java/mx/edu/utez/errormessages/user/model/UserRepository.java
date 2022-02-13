package mx.edu.utez.errormessages.user.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsernameAndPassword(String username, String password);
    Optional<User> findFirstByUsername(String username);
}
