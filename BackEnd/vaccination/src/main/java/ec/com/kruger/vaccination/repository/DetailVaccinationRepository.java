package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.DetailVaccination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DetailVaccinationRepository extends JpaRepository<DetailVaccination, Integer> {
    List<DetailVaccination> findByVaccineType(String type);
    List<DetailVaccination> findByVaccineDateBetween(Date initialDate, Date finalDate);
    List<DetailVaccination> findByIdNotLike(Integer id);
}