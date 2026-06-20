package dto;

import test.tests.BaseTest;

import static java.lang.String.valueOf;

public class UserTestDataFactory extends BaseTest {

    public static UserTestData getUserTestData() {
        return UserTestData.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .age(valueOf(faker.number().numberBetween(18, 99)))
                .money(faker.number().digits(10))
                .build();
    }
}
