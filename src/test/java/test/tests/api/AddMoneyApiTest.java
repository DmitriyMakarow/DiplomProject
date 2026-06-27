package test.tests.api;

import api.adapters.UserAdapter;
import api.models.CarRequest;
import api.models.UserReguest;
import api.models.UserResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import test.tests.BaseTest;

import static api.adapters.UserAdapter.checkAddMoneyUser;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

@Epic("Начисление денег пользователю. API")
@Feature("Начисление денег пользователю")
public class AddMoneyApiTest extends BaseTest {

    @Test(testName = "Начисление денег пользователю")
    public void checkAddMoney(){
        final int amount = faker.number().numberBetween(1, 100);

        UserResponse initialBalance = UserAdapter.getUserBalance();
        Integer balance = initialBalance.money;

        UserResponse updatedUser = checkAddMoneyUser(amount);
        Integer expectedBalance = balance + amount;
        assertEquals(updatedUser.money, expectedBalance);
    }

}
