package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.DetailVaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DetailVaccinationRepository extends JpaRepository<DetailVaccination, Integer> {
    List<DetailVaccination> findByCodEmployee(Integer code);
    List<DetailVaccination> findByVaccineType(String type);
    List<DetailVaccination> findByVaccineDateBetween(LocalDate initialDate, LocalDate finalDate);
}