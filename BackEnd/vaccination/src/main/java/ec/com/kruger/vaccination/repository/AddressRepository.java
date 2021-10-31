package ec.com.kruger.vaccination.repository;

import ec.com.kruger.vaccination.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}