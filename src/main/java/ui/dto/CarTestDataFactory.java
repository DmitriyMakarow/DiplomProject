package ui.dto;

import api.models.CarRequest;

import static ui.pages.base.BasePage.faker;

public class CarTestDataFactory {

    public static CarRequest validCarTestDataUI() {
        return CarRequest.builder()
                .engineType("Electric")
                .mark(faker.vehicle().manufacturer())
                .model(faker.vehicle().model())
                .price(faker.number().randomDouble(2, 500000, 1000000))
                .build();
    }
}
