package ui.enumUI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TableType {

    READ_ALL_USERS("Read all", List.of("ID", "First", "Last", "Age", "Sex", "Money")),
    READ_ALL_CARS("Read all", List.of("ID", "Engine", "Mark", "Model", "Price")),
    CREATE_NEW_CARS("Create new", List.of("ID", "Engine", "Mark", "Model", "Price")),
    CREATE_NEW_USER("Create new", List.of("ID", "First", "Last", "Age", "Sex", "Money")),
    CREATE_NEW_HOUSES("Create new", List.of("ID", "Floors", "Price")),
    ISSUE_A_LOAN("Issue a loan", List.of("User ID", "Размер кредита"));


    private final String tableName;
    private final List<String> columns;

    @Override
    public String toString() {
        return tableName;
    }
}
