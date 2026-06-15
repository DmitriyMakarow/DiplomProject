package test.tests;

import io.qameta.allure.Step;
import org.testng.annotations.Test;

public class AddMoneyTest extends BaseTest{
    @Test(testName = "Добавление денег  существующему пользователю", description = "Проверка корректности зачисления " +
            "денежных средств на баланс существующего пользователя через интерфейс")
        public void addMoneyPositiveTest() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, 1)
                .validationAddMoneyToUser();

    }

    @Test(testName = "Снятие денег (ошибочно) у пользователя через сервис добавление денег", description = "Проверка " +
            "невозможности списания денежных средств через эндпоинт добавления денег")
    public void takeOffMoneyNegativeTest() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, -1)
                .validationNotAddMoneyToUser("Status: Incorrect input data");
    }

    @Test(testName = "Добавление денег  несуществующему пользователю", description = "Проверка реакции системы при " +
            "попытке добавить деньги пользователю с несуществующим ID")
    public void addMoneyToNonExistentNegativeTest() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(1000000, 1)
                .validationNotAddMoneyToUser("Status: AxiosError: Request failed with status code 404");
    }
}
