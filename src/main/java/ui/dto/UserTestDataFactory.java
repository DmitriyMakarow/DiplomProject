package ui.dto;

import api.models.UserRequest;

import static java.lang.String.valueOf;
import static ui.pages.base.BasePage.faker;

public class UserTestDataFactory {

    public static UserTestData getUserTestDataUI() {
        return UserTestData.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .age(valueOf(faker.number().numberBetween(18, 99)))
                .money(faker.number().digits(10))
                .build();
    }

    public static UserRequest postUserTestDataApi() {
        return UserRequest.builder()
                .firstName(faker.name().firstName())
                .secondName(faker.name().lastName())
                .age(faker.number().numberBetween(18L, 99L))
                .sex("MALE")
                .money(faker.number().randomDouble(2, 100, 100000))
                .build();
    }

    public static UserRequest putUserTestDataApi() {
        return UserRequest.builder()
                .firstName(faker.name().firstName())
                .secondName(faker.name().lastName())
                .age(faker.number().numberBetween(18L, 99L))
                .sex("FEMALE")
                .money(faker.number().randomDouble(2, 100, 100000))
                .build();
    }

    public static UserRequest userMuchMoneyTestDataApi() {
        return UserRequest.builder()
                .firstName(faker.name().firstName())
                .secondName(faker.name().lastName())
                .age(faker.number().numberBetween(18L, 99L))
                .sex("FEMALE")
                .money(faker.number().randomDouble(2, 1000000, 1500000))
                .build();
    }
}
