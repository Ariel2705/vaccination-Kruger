package ec.com.kruger.vaccination.model;

import javax.persistence.*;

@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_user", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cod_employee", nullable = false)
    private Employee codEmployee;

    @Column(name = "username", nullable = false, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @Column(name = "role", nullable = false, length = 3)
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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