package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;


public class LoginTest extends BaseTest {

    @Test()
    @Description("Авторизация с валидными данными")
    @Story("Авторизация с валидными данными")
    public void checkPositiveCred() {
        loginPage
                .authorization("user@pflb.ru", "user")
                .verifySuccessAuthorization();
    }
}
