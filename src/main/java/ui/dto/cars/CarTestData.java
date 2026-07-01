package ui.dto.cars;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@AllArgsConstructor
@Data
@Builder
public class CarTestData {

    private String engineType;
    private String mark;
    private String model;
    private String price;
}
