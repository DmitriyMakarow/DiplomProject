package tests.ui.login;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

@Story("Авторизация")
public class LoginTest extends BaseTest {

    @Test(testName = "Авторизация с валидными данными")
    @Description("Авторизация с валидными данными")
    public void checkPositiveCred() {
        loginPage
                .authorization("user@pflb.ru", "user")
                .verifySuccessAuthorization();
    }

    @Test(testName = "Авторизация с пустым полем user")
    @Description("Авторизация с пустым полем user")
    public void checkEmptyUserLogin() {
        loginPage
                .login(" ", password)
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidUser("email cannot be empty");
    }

    @Test(testName = "Авторизация с некорректным данными в поле user")
    @Description("Авторизация с некорректным данными в поле user")
    public void checkInvalidUserLogin() {
        loginPage
                .login("abc", "")
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidUser("incorrect Email");
    }

    @Test(testName = "Авторизация с полем password менее чем из 3 символов")
    @Description("Авторизация с полем password менее чем из 3 символов")
    public void checkShortPasswordLogin() {
        loginPage
                .login(user, "ab")
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidPassword("password length must be more than 3 symbols and less than 8 symbols");
    }

    @Test(testName = "Авторизация с полем password более чем из 3 символов")
    @Description("Авторизация с полем password более чем из 8 символов")
    public void checkLongPasswordLogin() {
        loginPage
                .login(user, "qwertyuiop")
                .verifyErrorAuthorization("Incorrect input data")
                .verifyErrorMessageInvalidPassword("password length must be more than 3 symbols and less than 8 symbols");
    }

    @Test(testName = "Авторизация с несуществующими данными")
    @Description("Авторизация с несуществующими данными")
    public void checkInvalidLogin() {
        loginPage
                .login("user@mail.ru", "password")
                .verifyErrorAuthorization("Bad request");
    }
}
