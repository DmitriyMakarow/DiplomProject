package dto;

import api.models.UserRequest;
import test.tests.BaseTest;

import static java.lang.String.valueOf;

public class UserTestDataFactory extends BaseTest {

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
}
