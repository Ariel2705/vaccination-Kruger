package ec.com.kruger.vaccination.enums;

public enum StateEmployeeEnum {

    ACTIVE("ACT", "Active employee"),
    INACTIVE("INA", "Deleted employee");

    private final String state;
    private final String description;

    private StateEmployeeEnum(String state, String description){
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
