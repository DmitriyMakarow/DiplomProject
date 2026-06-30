package ui.pages.users;

import ui.dto.users.UserTestData;
import api.models.users.InvalidUserRequest;
import org.testng.annotations.DataProvider;
import ui.pages.base.BasePage;
import ui.wrappers.Input;

import static ui.enumUI.RadioLabel.FEMALE;
import static ui.enumUI.RadioLabel.MALE;
import static java.lang.String.valueOf;

public class UsersPage extends BasePage {

    @DataProvider(name = "UI. Тестовые данные для негативных проверок создания пользователя")
    public Object[][] userDataUI() {
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

    @DataProvider(name = "UI. Тестовые данные с некорректными значениями для пользователя")
    public Object[][] invalidUserDataUI() {
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

    @DataProvider(name = "Api. Тестовые данные для негативных проверок создания пользователя")
    public Object[][] userDataApi() {
        return new Object[][] {
                {InvalidUserRequest.builder()
                        .firstName("")
                        .secondName(faker.name().lastName())
                        .age(faker.number().numberBetween(18, 99))
                        .sex("MALE")
                        .money(faker.number().randomDouble(2, 100, 100000))
                        .description("Пустое имя")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName("A".repeat(256))
                        .secondName(faker.name().lastName())
                        .age(faker.number().numberBetween(18, 99))
                        .sex("MALE")
                        .money(faker.number().randomDouble(2, 100, 100000))
                        .description("Превышение максимальной длины firstName (256 символов)")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName(faker.name().firstName())
                        .secondName("")
                        .age(faker.number().numberBetween(18, 99))
                        .sex("MALE")
                        .money(faker.number().randomDouble(2, 100, 100000))
                        .description("Пустая фамилия")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName(faker.name().firstName())
                        .secondName("B".repeat(256))
                        .age(faker.number().numberBetween(18, 99))
                        .sex("MALE")
                        .money(faker.number().randomDouble(2, 100, 100000))
                        .description("Превышение максимальной длины secondName (256 символов)")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName(faker.name().firstName())
                        .secondName(faker.name().lastName())
                        .sex("MALE")
                        .age(2147483648L)
                        .money(faker.number().randomDouble(2, 100, 100000))
                        .description("Превышение максимального возраста (2147483648)")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName(faker.name().firstName())
                        .secondName(faker.name().lastName())
                        .age(faker.number().numberBetween(18, 99))
                        .sex("MALE")
                        .money("Hello")
                        .description("Строка вместо числа (money = 'Hello')")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName(faker.name().firstName())
                        .secondName(faker.name().lastName())
                        .age(faker.number().numberBetween(18, 99))
                        .sex("MALE")
                        .money("123abc")
                        .description("Строка с буквами и цифрами (money = '123abc')")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName(faker.name().firstName())
                        .secondName(faker.name().lastName())
                        .age(faker.number().numberBetween(18, 99))
                        .money(faker.number().randomDouble(2, 100, 100000))
                        .sex("")
                        .description("Пустой пол (sex = '')")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName("John123")
                        .secondName("Doe")
                        .age(150)
                        .money(50000.0)
                        .sex("MALE")
                        .description("Имя содержит цифры (John123)")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName("John")
                        .secondName("Doe!@#")
                        .age(150)
                        .money(50000.0)
                        .sex("FEMALE")
                        .description("Фамилия содержит спецсимволы (Doe!@#)")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName("John")
                        .secondName("Doe")
                        .age(-200)
                        .money(50000.0)
                        .sex("MALE")
                        .description("Отрицательный возраст (age = -200)")
                        .build()},

                {InvalidUserRequest.builder()
                        .firstName("")
                        .secondName("")
                        .age(-10)
                        .sex("UNKNOWN")
                        .money("abc")
                        .description("Комбинация невалидных данных (пустое имя + пустая фамилия + " +
                                "отрицательный возраст + неизвестный пол + строка в money)")
                        .build()}
        };
    }

    public void addNewUserUI(UserTestData userTestData) {
        new Input("first_name").fillField(userTestData.getFirstName());
        new Input("last_name").fillField(userTestData.getLastName());
        new Input("age").fillField(userTestData.getAge());
        new Input("money").fillField(userTestData.getMoney());
    }
}
