package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.Parish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParishRepository extends JpaRepository<Parish, Integer> {
    List<Parish> findByNameCanton(String nameCanton);
}