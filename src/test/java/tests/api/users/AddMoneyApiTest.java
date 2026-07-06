package tests.api.users;

import api.models.users.UserRequest;
import api.models.users.UserResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.users.UserTestDataFactory;

import static org.testng.Assert.assertEquals;
import static ui.pages.base.BasePage.faker;

@Epic("Начисление денег пользователю. API")
@Feature("Начисление денег пользователю")
public class AddMoneyApiTest extends BaseTest {

    private Integer userId;

    @BeforeMethod
    public void createUserApi() {
        UserRequest userRequest = UserTestDataFactory.postUserTestDataApi();
        UserResponse userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        if (userId != null) {
            userAdapter.deleteUser(userId);
        }
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Успешное перечисление денег пользователю", groups = {"regression"})
    @Description("Проверка успешного перечисления денег пользователю")
    public void checkAddMoney(){
        double initialBalance = userAdapter.getUserBalance(userId);
        final double amount = faker.number().numberBetween(1, 100);
        UserResponse userUpdateInfo = userAdapter.postUserMoney(userId, amount);
        double finalBalance = userAdapter.getUserBalance(userId);
        assertEquals(finalBalance, initialBalance + amount,
                "Баланс пользователя не увеличился на сумму начисления.");
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Успешное перечисление нулевой суммы", groups = {"regression"})
    @Description("Проверка перечисления нулевой суммы")
    public void checkAddMoneyEmptyAmount() {
        double initialBalance = userAdapter.getUserBalance(userId);
        final double amount = 0;
        UserResponse userUpdateInfo = userAdapter.postUserMoney(userId, amount);
        double finalBalance = userAdapter.getUserBalance(userId);
        assertEquals(finalBalance, initialBalance + amount,
                "Баланс пользователя изменился.");
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Успешное перечисление максимальной суммы", groups = {"regression"})
    @Description("Проверка перечисления максимальной суммы")
    public void checkAddMoneyMaxAmount() {
        double initialBalance = userAdapter.getUserBalance(userId);
        final double amount = 999999999999999.99;
        UserResponse userUpdateInfo = userAdapter.postUserMoney(userId, amount);
        double finalBalance = userAdapter.getUserBalance(userId);
        assertEquals(finalBalance, initialBalance + amount,
                "Баланс пользователя изменился.");
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Успешное перечисление дробной суммы", groups = {"regression"})
    @Description("Проверка перечисления дробной суммы")
    public void checkAddMoneyFractionalAmount() {
        double initialBalance = userAdapter.getUserBalance(userId);
        final double amount = 0.1;
        for (int i = 0; i < 10; i++) {
            UserResponse userUpdateInfo = userAdapter.postUserMoney(userId, amount);
        }
        double finalBalance = userAdapter.getUserBalance(userId);
        assertEquals(finalBalance, initialBalance + 1,
                "Баланс пользователя изменился не корректно");
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Негативный тест. Перечисление денег несуществующему пользователю",
          groups = {"regression"})
    @Description("Проверка перечисления денег несуществующему пользователю")
    public void checkAddMoneyEmptyUser() {
        int nonExistentUserId = 0;
        final double amount = faker.number().numberBetween(1, 100);
        Response response = userAdapter.negativePostUserMoney(nonExistentUserId, amount);
        usersSteps.validateStatusCode(response, 404);
    }

    @Owner("Квасникова О.Н.")
    @Test(testName = "Негативный тест. Снятие деньги через эндпоинт начисления", groups = {"regression"})
    @Description("Проверка снятия деньги через эндпоинт начисления")
    public void checkAddMoneyNegativeAmount() {
        final double amount = -100;
        Response response = userAdapter.negativePostUserMoney(userId, amount);
        usersSteps.validateStatusCode(response, 400);
    }
}
