package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findById(String id);
}