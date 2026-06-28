package ui.dto;

import static java.lang.String.valueOf;
import static ui.pages.base.BasePage.faker;

public class UserTestDataFactory {

    public static UserTestData getUserTestData() {
        return UserTestData.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .age(valueOf(faker.number().numberBetween(18, 99)))
                .money(faker.number().digits(10))
                .build();
    }
}
