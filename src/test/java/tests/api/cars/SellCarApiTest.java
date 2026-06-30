package tests.api.cars;

import api.models.cars.CarRequest;
import api.models.cars.CarResponse;
import api.models.users.UserRequest;
import api.models.users.UserResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.cars.CarTestDataFactory;
import ui.dto.users.UserTestDataFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.testng.Assert.*;

@Log4j2
@Epic("Автомобили. API")
@Feature("Продажа автомобиля")
public class SellCarApiTest extends BaseTest {

    private CarResponse carResponse;
    private int carId, userId;
    private double startMoney;

    @BeforeMethod
    public void createCarApi() {
        CarRequest carRequest = CarTestDataFactory.validCarTestDataAPI();
        carResponse = carAdapter.createApiCar(carRequest);
        carId = carResponse.getId();
        UserRequest userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        UserResponse userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
        startMoney = userResponse.getMoney();
    }

    @AfterMethod
    void deleteTestDate() {
        carAdapter.deleteApiCar(carId);
        userAdapter.deleteUser(userId);
    }

    @Issue("Сумма продажи не начисляется на счет пользователя")
    @Test(testName = "Успешная продажа автомобиля")
    @Description("Проверка продажи автомобиля из собственности пользователя")
    void successBuyCar() {
        carAdapter.successBuyApiCar(userId, carId);

        UserResponse userAfterSellResponse = carAdapter.successSellApiCar(userId, carId);
        userAdapter.getEmptyListCarsByUser(userId);

        BigDecimal bd = new BigDecimal(startMoney + carResponse.getPrice()).setScale(2, RoundingMode.HALF_UP);
        double roundedMoneyAfterSell = bd.doubleValue();
        assertEquals(userAfterSellResponse.getMoney(), roundedMoneyAfterSell,
                """
                        Сумма денег у пользователя не увеличилась на стоимость автомобиля
                        Ожидаемая сумма: %s
                        Фактическиая сумма: %s
                        Стоимость проданного автомобиля: %s"""
                        .formatted(roundedMoneyAfterSell, userAfterSellResponse.getMoney(), carResponse.getPrice()));
    }

    @Issue("Запрос на продажу выполняется успешно")
    @Test(testName = "Ошибка при продаже автомобиля")
    @Description("Проверка продажи автомобиля не находящегося в собственности пользователя")
    void buyNoEnoughMoneyCar() {
        userAdapter.getEmptyListCarsByUser(userId);
        UserResponse userAfterSellResponse = carAdapter.sellNoHaveApiCar(userId, carId);
        assertEquals(userAfterSellResponse.getMoney(), startMoney, "Сумма денег у пользователя изменилась!");
    }
}
