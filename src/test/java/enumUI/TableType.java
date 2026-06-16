package enumUI;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum TableType {

    READ_ALL_USERS("Read all", List.of("Reload", "ID", "First", "Last", "Age", "Sex", "Money")),
    READ_ALL_CARS("Read all", List.of("Reload", "ID", "Engine", "Mark", "Model", "Price"));

    private final String tableName;
    private final List<String> columns;

    @Override
    public String toString() {
        return tableName;
    }
}
