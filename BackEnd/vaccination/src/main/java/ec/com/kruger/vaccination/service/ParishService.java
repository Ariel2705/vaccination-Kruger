package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.model.Parish;
import ec.com.kruger.vaccination.repository.ParishRepository;
import ec.com.kruger.vaccination.security.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ParishService {

    private final ParishRepository parishRepo;

    public ParishService(ParishRepository parishRepo) {
        this.parishRepo = parishRepo;
    }

    public List<Parish> listParishesByNameCanton(String canton) throws DocumentNotFoundException {
        List<Parish> parishes = this.parishRepo.findByNameCanton(canton);
        if (!parishes.isEmpty()) {
            log.info("Listing the parishes of the canton: " + canton);
            return parishes;
        } else {
            log.error("There are no parishes for the canton: " + canton);
            throw new DocumentNotFoundException("There are no registered parishes in the canton: " + canton);
        }
    }
}
