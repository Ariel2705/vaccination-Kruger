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
    private String names;
    private String surnames;
    private String email;
    private LocalDate birthdate;
    private String phone;
    private String state;
    private String stateVaccination;
    private Integer codParish;
    private String mainAddress;
    private String sideStreet;
    private String vaccineType;
    private LocalDate vaccineDate;
    private Integer vaccineDoses;
}
