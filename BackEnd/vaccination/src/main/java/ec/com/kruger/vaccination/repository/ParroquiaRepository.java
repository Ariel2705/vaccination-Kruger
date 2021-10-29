package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.Parroquia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParroquiaRepository extends JpaRepository<Parroquia, Integer> {
    List<Parroquia> findByCanton(String canton);
}