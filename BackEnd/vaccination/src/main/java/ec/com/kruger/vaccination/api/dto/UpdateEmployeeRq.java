package ec.com.kruger.vaccination.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRq {

    private String identification;
    private LocalDate birthDate;
    private String phone;
    private Integer codParish;
    private String mainAddress;
    private String sideStreet;
}
