package ec.com.kruger.vaccination.model;

import javax.persistence.*;

@Table(name = "address", indexes = {
        @Index(name = "relationship_6_fk", columnList = "cod_parroquia")
})
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_address", nullable = false)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}