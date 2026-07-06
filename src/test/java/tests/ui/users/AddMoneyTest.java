package tests.ui.users;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

public class AddMoneyTest extends BaseTest {
    @Owner("Квасникова О.Н.")
    @Test(testName = "Добавление денег  существующему пользователю",
          groups = {"regression"})
    @Description("Проверка корректности зачисления денежных средств на баланс существующего пользователя через интерфейс")
        public void addMoneyPositiveTest() {
    loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, 1)
                .validationAddMoneyToUser();
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Снятие денег (ошибочно) у пользователя через сервис добавление денег",
          groups = {"regression"})
    @Description("Проверка невозможности списания денежных средств через эндпоинт добавления денег")
    public void takeOffMoneyNegativeTest() {
        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, -1)
                .validationNotAddMoneyToUser("Status: Incorrect input data");
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Добавление денег  несуществующему пользователю",
          groups = {"regression"})
    @Description("Проверка реакции системы при попытке добавить деньги пользователю с несуществующим ID")
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
