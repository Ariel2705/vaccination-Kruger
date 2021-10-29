package ec.com.kruger.vaccination.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "employee", indexes = {
        @Index(name = "relationship_4_fk", columnList = "cod_address")
})
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_employee", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_address", nullable = false)
    private Address codAddress;

    @Column(name = "identification", nullable = false, precision = 10)
    private BigDecimal identification;

    @Column(name = "names", nullable = false, length = 32)
    private String names;

    @Column(name = "surnames", nullable = false, length = 32)
    private String surnames;

    @Column(name = "email", nullable = false, length = 32)
    private String email;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "phone", nullable = false, length = 16)
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public BigDecimal getIdentification() {
        return identification;
    }

    public void setIdentification(BigDecimal identification) {
        this.identification = identification;
    }

    public Address getCodAddress() {
        return codAddress;
    }

    public void setCodAddress(Address codAddress) {
        this.codAddress = codAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}