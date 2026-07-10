package tests.ui.login;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;

@Story("Авторизация")
public class LoginTest extends BaseTest {

    @Test(testName = "Авторизация с валидными данными",
          groups = {"regression"})
    @Description("Авторизация с валидными данными")
    public void checkPositiveCred() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Авторизация с валидными данными")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Авторизация");
        parameter("Ожидаемый результат", "Успешная авторизация");

        loginPage
                .authorization("user@pflb.ru", "user")
                .verifySuccessAuthorization();
    }

    @Test(testName = "Авторизация с пустым полем user",
            groups = {"regression"})
    @Description("Авторизация с пустым полем user")
    public void checkEmptyUserLogin() {
        Allure.getLifecycle().updateTestCase(testCase ->
                testCase.setName("Авторизация с пустым полем user")
        );
        Allure.parameter("Тип теста", "Негативный");
        Allure.parameter("Действие", "Авторизация с пустым полем user");
        Allure.parameter("Ожидаемый результат", "Ошибка валидации");

        loginPage
                .login(" ", password)
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidUser("email cannot be empty");
    }

    @Test(testName = "Авторизация с некорректным данными в поле user",
            groups = {"regression"})
    @Description("Авторизация с некорректным данными в поле user")
    public void checkInvalidUserLogin() {
        Allure.getLifecycle().updateTestCase(testCase ->
                testCase.setName("Авторизация с некорректным данными в поле user")
        );
        Allure.parameter("Тип теста", "Негативный");
        Allure.parameter("Действие", "Авторизация с некорректным email");
        Allure.parameter("Ожидаемый результат", "Ошибка валидации");

        loginPage
                .login("abc", "")
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidUser("incorrect Email");
    }

    @Test(testName = "Авторизация с полем password менее чем из 3 символов",
            groups = {"regression"})
    @Description("Авторизация с полем password менее чем из 3 символов")
    public void checkShortPasswordLogin() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Авторизация с полем password менее чем из 3 символов")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Авторизация с коротким паролем");
        parameter("Ожидаемый результат", "Ошибка валидации");

        loginPage
                .login(user, "ab")
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidPassword("password length must be more than 3 symbols and less than 8 symbols");
    }

    @Test(testName = "Авторизация с полем password более чем из 3 символов",
            groups = {"regression"})
    @Description("Авторизация с полем password более чем из 8 символов")
    public void checkLongPasswordLogin() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Авторизация с полем password более чем из 8 символов")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Авторизация с длинным паролем");
        parameter("Ожидаемый результат", "Ошибка валидации");

        loginPage
                .login(user, "qwertyuiop")
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidPassword("password length must be more than 3 symbols and less than 8 symbols");
    }

    @Test(testName = "Авторизация с несуществующими данными",
            groups = {"regression"})
    @Description("Авторизация с несуществующими данными")
    public void checkInvalidLogin() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Авторизация с несуществующими данными")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Авторизация с несуществующими данными");
        parameter("Ожидаемый результат", "Ошибка авторизации");

        loginPage
                .login("user@mail.ru", "password")
                .verifyErrorAuthorization("Bad request");
    }
}
