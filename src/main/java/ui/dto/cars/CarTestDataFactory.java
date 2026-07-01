package ui.dto.cars;

import api.models.cars.CarRequest;

import static ui.pages.base.BasePage.faker;

public class CarTestDataFactory {

    public static CarTestData validCarTestDataUI() {
        return CarTestData.builder()
                .engineType("Gasoline")
                .mark(faker.vehicle().manufacturer())
                .model(faker.vehicle().model())
                .price(faker.number().digits(7))
                .build();
    }

    public static CarRequest emptyCarTestDataUI() {
        return CarRequest.builder()
                .engineType("")
                .mark("")
                .model("")
                .price(0)
                .build();
    }

    public static CarRequest validCarTestDataAPI() {
        return CarRequest.builder()
                .engineType("Electric")
                .mark(faker.vehicle().manufacturer())
                .model(faker.vehicle().model())
                .price(faker.number().randomDouble(2, 500000, 1000000))
                .build();
    }
}
