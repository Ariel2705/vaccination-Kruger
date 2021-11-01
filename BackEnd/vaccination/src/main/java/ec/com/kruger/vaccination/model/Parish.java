package ec.com.kruger.vaccination.model;

import lombok.Data;

import javax.persistence.*;

@Table(name = "parish")
@Entity
@Data
public class Parish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_parish", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "name_canton", nullable = false, length = 32)
    private String nameCanton;

}