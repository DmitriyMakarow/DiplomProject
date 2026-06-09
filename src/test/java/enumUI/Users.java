package enumUI;

public enum Users {
    RELOAD("Reload"),
    ID("ID"),
    FIRST_NAME("First"),
    LAST_NAME("Last"),
    AGE("Age"),
    SEX("Sex"),
    MONEY("Money");

    private final String buttonName;


    Users(String buttonName) {
        this.buttonName = buttonName;
    }

    @Override
    public String toString() {
        return buttonName;
    }
}
