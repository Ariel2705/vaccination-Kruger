package ec.com.kruger.vaccination.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "employee")
@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_employee", nullable = false)
    private Integer id;

    @JoinColumn(name = "cod_address", nullable = false)
    private Integer codAddress;

    @Column(name = "identification", precision = 10)
    private String identification;

    @Column(name = "names", nullable = false, length = 32)
    private String names;

    @Column(name = "surnames", nullable = false, length = 32)
    private String surnames;

    @Column(name = "email", nullable = false, length = 32)
    private String email;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "phone", length = 16)
    private String phone;

    @Column(name = "state", nullable = false, length = 3)
    private String state;

    @Column(name = "state_vaccination", nullable = false, length = 3)
    private String stateVaccination;
}