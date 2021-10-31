package ec.com.kruger.vaccination.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRq {

    private String identification;
    private Date birthDate;
    private String phone;
    private Integer codParish;
    private String mainAddress;
    private String sideStreet;
}
