package tests.ui.users;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;

import static io.qameta.allure.Allure.getLifecycle;
import static io.qameta.allure.Allure.parameter;

@Epic("Пользователи")
@Feature("Действия с балансом")
public class AddMoneyTest extends BaseTest {

    @Owner("Квасникова О.Н.")
    @Test(groups = {"regression"})
    @Description("Добавление денег существующему пользователю")
    void addMoneyPositiveTest() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Добавление денег существующему пользователю")
        );
        parameter("Тип теста", "Позитивный");
        parameter("Действие", "Добавление денег пользователю");
        parameter("Ожидаемый результат", "Деньги успешно добавлены");

        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, 1)
                .validationAddMoneyToUser();
    }

    @Owner("Квасникова О.Н.")
    @Test(groups = {"regression"})
    @Description("Проверка невозможности списания денежных средств через эндпоинт добавления денег")
    public void takeOffMoneyNegativeTest() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка невозможности списания денежных средств через эндпоинт добавления денег")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Попытка списания денег через эндпоинт добавления");
        parameter("Ожидаемый результат", "Ошибка, списание невозможно");

        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(6936, -1)
                .validationNotAddMoneyToUser("Status: Incorrect input data");
    }

    @Owner("Квасникова О.Н.")
    @Test(groups = {"regression"})
    @Description("Проверка реакции системы при попытке добавить деньги пользователю с несуществующим ID")
    public void addMoneyToNonExistentNegativeTest() {
        getLifecycle().updateTestCase(testCase ->
                testCase.setName("Проверка реакции системы при попытке добавить деньги пользователю с несуществующим ID")
        );
        parameter("Тип теста", "Негативный");
        parameter("Действие", "Добавление денег несуществующему пользователю");
        parameter("Ожидаемый результат", "Ошибка, пользователь не найден");

        loginPage
                .authorization(user, password)
                .verifySuccessAuthorization();
        addMoneyPage
                .openPage()
                .addMoneyToUser(1000000, 1)
                .validationNotAddMoneyToUser("Status: AxiosError: Request failed with status code 404");
    }
}
