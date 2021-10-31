package ec.com.kruger.vaccination.enums;

public enum NewEmployeeEnum {
    PASS("Kruger123", "Contrasena por defecto"),
    ROLE("EMP", "Rol de empleado");

    private final String state;
    private final String description;

    private NewEmployeeEnum(String state, String description){
        this.state = state;
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }
}
