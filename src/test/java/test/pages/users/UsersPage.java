package test.pages.users;

import dto.UserTestData;
import org.testng.annotations.DataProvider;
import test.pages.base.BasePage;
import wrappers.Input;

import static enumUI.RadioLabel.FEMALE;
import static enumUI.RadioLabel.MALE;
import static java.lang.String.valueOf;

public class UsersPage extends BasePage {

    @DataProvider(name = "Тестовые данные для негативных проверок создания пользователя")
    public Object[][] userData() {
        return new Object[][] {
                {UserTestData.builder()
                        .firstName("")
                        .lastName(faker.name().lastName())
                        .age(valueOf(faker.number().numberBetween(18, 99)))
                        .money(faker.number().digits(10))
                        .gender(MALE)
                        .description("Пустое имя")
                        .build()},

                {UserTestData.builder()
                        .firstName(faker.name().firstName())
                        .lastName("")
                        .age(valueOf(faker.number().numberBetween(18, 99)))
                        .money(faker.number().digits(10))
                        .gender(FEMALE)
                        .description("Пустая фамилия")
                        .build()},

                {UserTestData.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .age("")
                        .money(faker.number().digits(10))
                        .gender(FEMALE)
                        .description("Пустой возраст")
                        .build()},

                {UserTestData.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .age(valueOf(faker.number().numberBetween(18, 99)))
                        .money("")
                        .gender(MALE)
                        .description("Пустые деньги")
                        .build()},

                {UserTestData.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .age(valueOf(faker.number().numberBetween(18, 99)))
                        .money(faker.number().digits(10))
                        .gender(null)
                        .description("Пустой пол")
                        .build()}
        };
    }

    @DataProvider(name = "Тестовые данные с некорректными значениями для пользователя")
    public Object[][] invalidUserData() {
        return new Object[][] {
                {UserTestData.builder()
                        .firstName("John123")
                        .lastName("Doe")
                        .age("25")
                        .money("5000")
                        .gender(MALE)
                        .description("Имя с цифрами")
                        .build()},

                {UserTestData.builder()
                        .firstName("John")
                        .lastName("Doe!@#")
                        .age("25")
                        .money("5000")
                        .gender(FEMALE)
                        .description("Фамилия со спецсимволами")
                        .build()},

                {UserTestData.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .age("2147483648")
                        .money("5000")
                        .gender(MALE)
                        .description("Превышение максимального значения на 1 у возраста")
                        .build()},

                {UserTestData.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .age("-5")
                        .money("5000")
                        .gender(MALE)
                        .description("Отрицательный возраст")
                        .build()}
        };
    }

    public void addNewUser(UserTestData userTestData) {
        new Input("first_name").fillField(userTestData.getFirstName());
        new Input("last_name").fillField(userTestData.getLastName());
        new Input("age").fillField(userTestData.getAge());
        new Input("money").fillField(userTestData.getMoney());
    }
}
