package ec.com.kruger.vaccination.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmployeeRq {

    private String identification;
    private Integer codAddress;
    private String names;
    private String surnames;
    private Date birthDate;
    private String email;
    private String state;
    private String phone;
    private String username;
    private String password;
}
