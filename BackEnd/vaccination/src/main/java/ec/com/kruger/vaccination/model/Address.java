package ec.com.kruger.vaccination.model;

import lombok.*;

import javax.persistence.*;

@Table(name = "address")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_address", nullable = false)
    private Integer id;

    @JoinColumn(name = "cod_parish", nullable = false)
    private Integer codParish;

    @Column(name = "main_address", nullable = false, length = 32)
    private String mainAddress;

    @Column(name = "side_street", nullable = false, length = 32)
    private String sideStreet;
}