package ec.com.kruger.vaccination.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "detail_vaccination")
@Entity
@Getter
@Setter
public class DetailVaccination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_vaccine", nullable = false)
    private Integer id;

    @JoinColumn(name = "cod_employee", nullable = false)
    private Integer codEmployee;

    @Column(name = "vaccine_type", nullable = false, length = 16)
    private String vaccineType;

    @Column(name = "vaccine_date", nullable = false)
    private LocalDate vaccineDate;

    @Column(name = "vaccine_doses", nullable = false)
    private Integer vaccineDoses;
}