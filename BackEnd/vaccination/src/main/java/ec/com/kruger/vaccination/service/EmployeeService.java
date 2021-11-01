package ec.com.kruger.vaccination.service;

import ec.com.kruger.vaccination.api.dto.CompleteDataEmployeeRq;
import ec.com.kruger.vaccination.api.dto.UpdateEmployeeRq;
import ec.com.kruger.vaccination.enums.NewEmployeeEnum;
import ec.com.kruger.vaccination.enums.StateEmployeeEnum;
import ec.com.kruger.vaccination.enums.StateVaccinationEnum;
import ec.com.kruger.vaccination.exception.DocumentNotFoundException;
import ec.com.kruger.vaccination.exception.InsertException;
import ec.com.kruger.vaccination.exception.UpdateException;
import ec.com.kruger.vaccination.model.Address;
import ec.com.kruger.vaccination.model.DetailVaccination;
import ec.com.kruger.vaccination.model.Employee;
import ec.com.kruger.vaccination.model.EmployeeUser;
import ec.com.kruger.vaccination.repository.AddressRepository;
import ec.com.kruger.vaccination.repository.DetailVaccinationRepository;
import ec.com.kruger.vaccination.repository.EmployeeRepository;
import ec.com.kruger.vaccination.repository.EmployeeUserRepository;
import ec.com.kruger.vaccination.security.Authorization;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;
    private final AddressRepository addressRepo;
    private final EmployeeUserRepository userRepo;
    private final DetailVaccinationRepository detailVaccinationRepo;

    @Value("${userAuthorization}")
    private String userAuthorization;

    @Value("${passwordAuthorization}")
    private String passwordAuthorization;

    @Value("${domainKruger}")
    private String domainKruger;

    public EmployeeService(EmployeeRepository employeeRepo, AddressRepository addressRepo,
                           EmployeeUserRepository userRepo, DetailVaccinationRepository detailVaccinationRepo) {
        this.employeeRepo = employeeRepo;
        this.addressRepo = addressRepo;
        this.userRepo = userRepo;
        this.detailVaccinationRepo = detailVaccinationRepo;
    }

    public void createEmployee(Employee employee) throws InsertException {
        try {
            if (this.employeeRepo.findByIdentification(employee.getIdentification()) != null) {
                log.error("This employee already exists.");
                throw new InsertException("Employee", "Error creating employee. Already exists.");
            } else {
                if (validateCI(employee.getIdentification()) &&
                        validateMail(employee.getEmail()) &&
                        validateNames(employee.getNames(), employee.getSurnames())) {
                    employee.setStateVaccination(StateVaccinationEnum.NO.getState());
                    employee.setState(StateEmployeeEnum.ACTIVE.getState());
                    log.info("Creating an employee.");
                    Employee employeeCreate = this.employeeRepo.saveAndFlush(employee);
                    log.info("Creating an user.");
                    this.userRepo.save(EmployeeUser.builder()
                            .codEmployee(employeeCreate.getId())
                            .username(employee.getIdentification())
                            .password(NewEmployeeEnum.PASS.getState())
                            .role(NewEmployeeEnum.ROLE.getState())
                            .build());
                } else {
                    log.error("Invalid fields.");
                    throw new InsertException("Employee", "Error creating user. Invalid fields.S");
                }
            }
        } catch (Exception e) {
            log.error("Error creating an employee.");
            throw new InsertException("Employee", "Error when creating the employee: " + employee.getIdentification(), e);
        }
    }

    public void updateEmployee(UpdateEmployeeRq updateEmployeeRq) throws UpdateException {
        try {
            Employee employee = this.employeeRepo.findByIdentification(updateEmployeeRq.getIdentification());
            if(employee != null) {
                log.info("Updating data of employee.");
                employee.setNames((updateEmployeeRq.getNames() != null)
                        ? updateEmployeeRq.getNames() : employee.getNames());
                employee.setSurnames((updateEmployeeRq.getSurnames() != null && validateNames(updateEmployeeRq.getNames(), updateEmployeeRq.getSurnames()))
                        ? updateEmployeeRq.getSurnames() : employee.getSurnames());
                employee.setEmail((updateEmployeeRq.getEmail() != null && validateMail(updateEmployeeRq.getEmail()))
                        ? updateEmployeeRq.getEmail() : employee.getEmail());
                employee.setBirthdate((updateEmployeeRq.getBirthdate() != null)
                        ? updateEmployeeRq.getBirthdate() : employee.getBirthdate());
                employee.setPhone((updateEmployeeRq.getPhone() != null)
                        ? updateEmployeeRq.getPhone() : employee.getPhone());
                employee.setState((updateEmployeeRq.getState() != null)
                        ? updateEmployeeRq.getState() : employee.getState());
                employee.setStateVaccination((updateEmployeeRq.getStateVaccination() != null)
                        ? updateEmployeeRq.getStateVaccination() : employee.getStateVaccination());
                if(employee.getCodAddress() != null){
                    log.info("Update address");

                    Optional<Address> address = this.addressRepo.findById(employee.getCodAddress());

                    address.get().setCodParish((updateEmployeeRq.getCodParish() != null)
                            ? updateEmployeeRq.getCodParish() : address.get().getCodParish());
                    address.get().setMainAddress((updateEmployeeRq.getMainAddress() != null)
                            ? updateEmployeeRq.getMainAddress() : address.get().getMainAddress());
                    address.get().setSideStreet((updateEmployeeRq.getSideStreet() != null)
                            ? updateEmployeeRq.getSideStreet() : address.get().getSideStreet());
                    this.addressRepo.save(address.get());
                } else {
                    throw new DocumentNotFoundException("No address assigned to the user.");
                }

                if(StateVaccinationEnum.SI.getState().equals(employee.getStateVaccination())){
                    log.info("Updating details");

                    DetailVaccination detail = this.detailVaccinationRepo.findByCodEmployee(employee.getId());

                    detail.setVaccineType((updateEmployeeRq.getVaccineType() != null)
                            ? updateEmployeeRq.getVaccineType() : detail.getVaccineType());
                    detail.setVaccineDate((updateEmployeeRq.getVaccineDate() != null)
                            ? updateEmployeeRq.getVaccineDate() : detail.getVaccineDate());
                    detail.setVaccineDoses((updateEmployeeRq.getVaccineDoses() != null)
                            ? updateEmployeeRq.getVaccineDoses() : detail.getVaccineDoses());

                    this.detailVaccinationRepo.save(detail);
                } else {
                    throw new DocumentNotFoundException("There is no vaccination detail for the user..");
                }
                this.employeeRepo.save(employee);
            } else {
                throw new DocumentNotFoundException("There is no employee to update.");
            }
        } catch (Exception e) {
            throw new UpdateException("Employee", "An error occurred while updating the employee: " + updateEmployeeRq.toString(), e);
        }
    }

    public void completeDataEmployee(CompleteDataEmployeeRq employee) throws UpdateException {
        try {
            Employee employeeUpdate = this.employeeRepo.findByIdentification(employee.getIdentification());
            if (employeeUpdate != null) {
                log.info("Updating employee data.");
                employeeUpdate.setBirthdate(employee.getBirthDate());
                employeeUpdate.setPhone(employee.getPhone());
                employeeUpdate.setStateVaccination(StateVaccinationEnum.SI.getState());
                Address addressCreate =
                        this.addressRepo.save(
                                Address.builder()
                                        .codParish(employee.getCodParish())
                                        .mainAddress(employee.getMainAddress())
                                        .sideStreet(employee.getSideStreet())
                                        .build());
                employeeUpdate.setCodAddress(addressCreate.getId());
                this.employeeRepo.save(employeeUpdate);
            } else {
                throw new DocumentNotFoundException("There is no employee to update his data.");
            }
        } catch (Exception e) {
            throw new UpdateException("Employee", "An error occurred while complete the data the employee: " + employee.toString(), e);
        }
    }

    public void changeStateEmployee(String identification) throws UpdateException {
        try {
            Employee employee = this.employeeRepo.findByIdentification(identification);
            if (employee != null) {
                log.info("Changing employee status.");
                employee.setState(StateEmployeeEnum.INACTIVE.getState());
                this.employeeRepo.save(employee);
            } else {
                throw new UpdateException("Employee", "Employee not found.");
            }
        } catch (Exception e) {
            throw new UpdateException("Employee", "Error updating employee status.", e);
        }
    }

    public Employee findEmployeeByIdentification(String identification) throws DocumentNotFoundException {
        log.info("Employee found.");
        Employee employee = this.employeeRepo.findByIdentification(identification);
        if (employee != null) {
            return employee;
        } else {
            throw new DocumentNotFoundException("Employee not found.");
        }
    }

    public List<Employee> findEmployeeByStateVaccination(String stateVaccination) throws DocumentNotFoundException {
        try {
            List<Employee> employees = this.employeeRepo.findByStateVaccinationAndState(stateVaccination, StateEmployeeEnum.ACTIVE.getState());
            if (!employees.isEmpty()) {
                log.info("Listing employees with vaccination status: " + stateVaccination);
                return employees;
            } else {
                throw new DocumentNotFoundException("Employees not found");
            }
        } catch (Exception e) {
            throw new DocumentNotFoundException("Error when searching for employees by vaccination status.");
        }
    }

    public List<Employee> findEmployeeByTypeVaccine(String vaccine) throws DocumentNotFoundException {
        try {
            Authorization authorizationRq = new Authorization("1804915617", "Kruger123");
            String authorization = authorizationRq.tokenAuthorization();
            List<Employee> employees = new ArrayList<>();
            List<DetailVaccination> request = Unirest
                    .get("http://localhost:8080/api/kruger/detailVaccination/findDetailByTypeVaccine/{type}")
                    .header("Authorization", authorization)
                    .routeParam("type", vaccine)
                    .asObject(new GenericType<List<DetailVaccination>>() {})
                    .getBody();
            if (!request.isEmpty()) {
                for (DetailVaccination detail: request) {
                    log.info("Listing employees by type of vaccine.");
                    employees.add(this.employeeRepo.findByIdAndState(detail.getCodEmployee(), StateEmployeeEnum.ACTIVE.getState()));
                }
                return employees;
            } else {
                throw new DocumentNotFoundException("There are no employees vaccinated with: " + vaccine);
            }
        } catch (Exception e) {
            throw new DocumentNotFoundException("Error when searching for employees by type of vaccine.");
        }
    }

    public List<Employee> findEmployeeByDates(LocalDate initialDate, LocalDate finalDate) throws DocumentNotFoundException {
        try {
            Authorization authorizationRq = new Authorization("1804915617", "Kruger123");
            String authorization = authorizationRq.tokenAuthorization();
            List<Employee> employees = new ArrayList<>();
            JSONObject object = new JSONObject();
            object.put("initialDate", initialDate);
            object.put("finalDate", finalDate);
            List<DetailVaccination> request = Unirest
                    .post("http://localhost:8080/api/kruger/detailVaccination/findDetailByDates")
                    .header("Content-Type", "application/json")
                    .header("Authorization", authorization)
                    .body(object)
                    .asObject(new GenericType<List<DetailVaccination>>() {})
                    .getBody();

            if (!request.isEmpty()) {
                for (DetailVaccination detail: request) {
                    log.info("Listing employees by vaccination date.");
                    employees.add(this.employeeRepo.findByIdAndState(detail.getCodEmployee(), StateEmployeeEnum.ACTIVE.getState()));
                }
                return employees;
            } else {
                throw new DocumentNotFoundException("There are no vaccinated employees between: " + initialDate.toString() + " y " + finalDate.toString());
            }
        } catch (Exception e) {
            throw new DocumentNotFoundException("Error, employees not found by vaccination date.");
        }
    }

    private boolean validateCI(String identification) {
        int sum = 0;
        int mul = 2;
        int res = 0;
        if (identification.length() == 10) {
            for (int i = 0; i < 9; i++) {
                int num = Integer.parseInt(Character.toString(identification.charAt(i))) * mul;
                sum += num >= 10 ? num - 9 : num;
                mul = i % 2 == 0 ? 1 : 2;
            }

            int superior = ((sum / 10) * 10) + 10;
            res = superior - sum >= 10 ? (superior - sum) - 10 : superior - sum;

            if (res == Integer.parseInt(Character.toString(identification.charAt(9)))) {
                return true;
            }
        }
        return false;
    }

    private boolean validateMail(String mail) {
        Pattern pat = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z])+(.[a-z]+))");
        Matcher mather = pat.matcher(mail);
        if (mather.find()) {
            return true;
        }
        return false;
    }

    private boolean validateNames(String names, String surnames) {
        Pattern pat = Pattern.compile("([A-Za-zÀ-ÿ\\u00f1\\u00d1]+)( )([A-Za-zÀ-ÿ\\u00f1\\u00d1]+)");
        Matcher matherName = pat.matcher(names);
        Matcher matherSurname = pat.matcher(surnames);
        if (matherName.find() && matherSurname.find()) {
            return true;
        }
        return false;
    }
}
