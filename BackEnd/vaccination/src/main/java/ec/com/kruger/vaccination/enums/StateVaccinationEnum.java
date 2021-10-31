package ec.com.kruger.vaccination.enums;

public enum StateVaccinationEnum {

    SI("SI", "Vaccinated employee"),
    NO("NO", "Non-vaccinated employee");

    private final String state;
    private final String description;

    private StateVaccinationEnum(String state, String description){
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
