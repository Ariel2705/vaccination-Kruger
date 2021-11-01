package ec.com.kruger.vaccination.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "employeeUser")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_user", nullable = false)
    private Integer id;

    @JoinColumn(name = "cod_employee", nullable = false)
    private Integer codEmployee;

    @Column(name = "username", nullable = false, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @Column(name = "role", nullable = false, length = 3)
    private String role;

}