package tests.ui.users;

import api.models.users.UserResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.users.UserTestDataFactory;
import ui.wrappers.Input;

import java.util.List;

import static java.lang.String.valueOf;
import static ui.enumUI.Dropdown.USERS;
import static ui.enumUI.TableType.READ_USER_WITH_CARS;

@Epic("Пользователи")
@Feature("Чтение пользователей с машинами")
public class ReadUserWithCarsUITest extends BaseTest {

    private Integer userId;
    private List<Integer> carsIdList;

    @BeforeMethod
    void createDataCar() {
        userId = userAdapter.createUser(UserTestDataFactory.userMuchMoneyTestDataApi()).getId();
        carsIdList = carAdapter.createCarsAndGetIds(10, 201);
        userAdapter.postUserMoney(userId, 2000000000.0);
    }

    @AfterMethod
    void deleteAll(){
        carAdapter.sellCars(userId, carsIdList, 200, UserResponse.class);
        carAdapter.deleteApiCars(carsIdList);
        userAdapter.deleteUser(userId);
    }

    @Owner("Лазарев Г.А.")
    @Test(testName = "Чтение пользователя с машинами")
    @Description("Проверка, что у пользователя есть машины")
    void shouldShowMachinesForUser() {
        carAdapter.buyCars(userId, carsIdList, 10, 200, UserResponse.class);
        loginPage.authorization();
        baseSteps.openTableFromDropdown(USERS, READ_USER_WITH_CARS);
        new Input("user_input").fillField(valueOf(userId));
        baseSteps.clickBtn("Read");
        usersSteps
                .assertUserAndCarsExist(userId, carsIdList)
                .assertCarCountMatches("tableUser","tableCars");
    }
}
