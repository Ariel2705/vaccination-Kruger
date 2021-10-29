package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUser(String user);
}