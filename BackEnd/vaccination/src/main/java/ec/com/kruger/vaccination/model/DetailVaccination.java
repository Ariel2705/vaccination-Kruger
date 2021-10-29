package ec.com.kruger.vaccination.model;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "detail_vaccination", indexes = {
        @Index(name = "relationship_1_fk", columnList = "cod_employee")
})
@Entity
public class DetailVaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_vaccine", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_employee", nullable = false)
    private Employee codEmployee;

    @Column(name = "vaccine_type", nullable = false, length = 16)
    private String vaccineType;

    @Column(name = "vaccine_date", nullable = false)
    private LocalDate vaccineDate;

    @Column(name = "vaccine_doses", nullable = false)
    private Integer vaccineDoses;

    public Integer getVaccineDoses() {
        return vaccineDoses;
    }

    public void setVaccineDoses(Integer vaccineDoses) {
        this.vaccineDoses = vaccineDoses;
    }

    public LocalDate getVaccineDate() {
        return vaccineDate;
    }

    public void setVaccineDate(LocalDate vaccineDate) {
        this.vaccineDate = vaccineDate;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public Employee getCodEmployee() {
        return codEmployee;
    }

    public void setCodEmployee(Employee codEmployee) {
        this.codEmployee = codEmployee;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}