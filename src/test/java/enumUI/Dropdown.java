package enumUI;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dropdown {

    /**
     * Users
     */
    USERS("Users"),
    READ_USER_WITH_CARS("Read user with cars"),
    CREATE_NEW_USERS("Create new"),
    ADD_MONEY("Add money"),
    BUY_OR_SELL_CAR_USERS("Buy or sell car"),
    SETTLE_TO_HOUSE("Settle to house"),
    ISSUE_A_LOAN("Issue a loan"),

    /**
     * Cars
     */
    CARS("Cars"),
    READ_ALL_CARS("Read all"),
    BUY_OR_SELL_CAR_CARS("Buy or sell car"),

    /**
     * Houses
     */
    READ_ALL_HOUSES("Read all"),
    READ_ONE_BY_ID("Read one by ID"),
    CREATE_NEW_HOUSES("Create new"),
    SETTLE_OR_EVICT_USER("Settle or evict user");

    private final String tableName;

    @Override
    public String toString() {
        return tableName;
    }
}
