package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByIdAndState(Integer id, String state);
    Employee findByIdentification(String identification);
    List<Employee> findByStateVaccinationAndState(String stateVaccination, String state);
}