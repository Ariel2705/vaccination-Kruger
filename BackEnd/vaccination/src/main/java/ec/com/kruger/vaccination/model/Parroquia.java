package ec.com.kruger.vaccination.model;

import javax.persistence.*;

@Table(name = "parroquia")
@Entity
public class Parroquia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_parroquia", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @Column(name = "name_canton", nullable = false, length = 32)
    private String nameCanton;

    public String getNameCanton() {
        return nameCanton;
    }

    public void setNameCanton(String nameCanton) {
        this.nameCanton = nameCanton;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}