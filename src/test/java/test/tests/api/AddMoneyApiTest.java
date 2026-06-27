package test.tests.api;

import api.adapters.UserAdapter;
import api.models.UserRequest;
import api.models.UserResponse;
import dto.UserTestDataFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.tests.BaseTest;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

@Epic("Начисление денег пользователю. API")
@Feature("Начисление денег пользователю")
public class AddMoneyApiTest extends BaseTest {

    private UserResponse userResponse;
    private UserRequest userRequest;
    private Integer userId;

    @BeforeMethod
    public void createUserApi() {
        userRequest = UserTestDataFactory.postUserTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanUp() {
        if (userId != null) {
            userAdapter.deleteUser(userId);
        }
    }

    @Test(testName = "Начисление денег пользователю")
    public void checkAddMoney(){

        UserResponse userUpdateInfo = userAdapter.postUserMoney(userId, 10000);
        final int amount = faker.number().numberBetween(1, 100);

//        UserResponse initialBalance = UserAdapter.getUserBalance();
//        double balance = initialBalance.money;
//
//        UserResponse updatedUser = checkAddMoneyUser(amount);
//        Integer expectedBalance = balance + amount;
//        assertEquals(updatedUser.money, expectedBalance);
    }

}
