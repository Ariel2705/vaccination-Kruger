package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.EmployeeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeUserRepository extends JpaRepository<EmployeeUser, Integer> {
    EmployeeUser findByUsername(String username);
}