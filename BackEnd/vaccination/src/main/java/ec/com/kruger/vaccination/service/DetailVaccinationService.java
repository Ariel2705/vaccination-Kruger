package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.exception.UpdateException;
import ec.com.kruger.vaccination.model.DetailVaccination;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.repository.DetailVaccinationRepository;
import ec.com.kruger.vaccination.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class DetailVaccinationService {

    private final DetailVaccinationRepository detailVaccinationRepo;
    private final EmployeeRepository employeeRepo;

    public DetailVaccinationService(DetailVaccinationRepository detailVaccinationRepo,
                                    EmployeeRepository employeeRepo){
        this.detailVaccinationRepo = detailVaccinationRepo;
        this.employeeRepo = employeeRepo;
    }

    public void createDetail(DetailVaccination detailVaccination, String identification) throws InsertException{
        try{
            Employee employeeDetail = this.employeeRepo.findByIdentification(identification);
            if (employeeDetail != null) {
                log.info("Creating vaccination detail.");
                detailVaccination.setCodEmployee(employeeDetail.getId());
                this.detailVaccinationRepo.save(detailVaccination);
            } else {
                throw new UpdateException("Detail", "There is no employee to create the vaccination detail.");
            }
        } catch (Exception e) {
            throw new InsertException("Detail", "Error when creating the vaccine detail: " + detailVaccination.toString(), e);
        }

    }

    public List<DetailVaccination> findDetailByTypeVaccine(String vaccine) throws DocumentNotFoundException {
        try{
            List<DetailVaccination> details = this.detailVaccinationRepo.findByVaccineType(vaccine);
            if(!details.isEmpty()) {
                log.info("List of details for the vaccine: " + vaccine);
                return details;
            } else {
                throw new DocumentNotFoundException("There are no records of details for the vaccine: " + vaccine);
            }
        } catch (Exception e) {
            throw new DocumentNotFoundException("Error when searching for details by vaccine type");
        }
    }

    public List<DetailVaccination> findDetailByDates(LocalDate initialDate, LocalDate finalDate) throws DocumentNotFoundException {
        try{
            List<DetailVaccination> details = this.detailVaccinationRepo.findByVaccineDateBetween(initialDate, finalDate);
            if(!details.isEmpty()) {
                log.info("List of details by dates.");
                return details;
            } else {
                throw new DocumentNotFoundException("No detail records exist for date range.");
            }
        } catch (Exception e) {
            throw new DocumentNotFoundException("Error when searching for details by date range.");
        }
    }

}
