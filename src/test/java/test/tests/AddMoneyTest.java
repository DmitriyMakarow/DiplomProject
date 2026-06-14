package test.tests;

import io.qameta.allure.Step;
import org.testng.annotations.Test;

public class AddMoneyTest extends BaseTest{
    @Test
    @Step("Добавление денег  существующему пользователю")
    public void addMoneyPositiveTest() {
        loginPage
                .authorization("user@pflb.ru", "user")
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, 1)
                .validationAddMoneyToUser();

    }

    @Test
    @Step("Снятие денег (ошибочно) у пользователя через сервис добавление денег")
    public void takeOffMoneyNegativeTest() {
        loginPage
                .authorization("user@pflb.ru", "user")
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, -1)
                .validationNotAddMoneyToUser("Status: Incorrect input data");
    }

    @Test
    @Step("Добавление денег несуществующему пользователю")
    public void addMoneyToNonExistentNegativeTest() {
        loginPage
                .authorization("user@pflb.ru", "user")
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(1000000, 1)
                .validationNotAddMoneyToUser("Status: AxiosError: Request failed with status code 404");
    }
}
