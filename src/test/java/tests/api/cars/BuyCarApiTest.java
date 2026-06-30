package tests.api.cars;

import api.models.*;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.CarTestDataFactory;
import ui.dto.UserTestDataFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.testng.Assert.*;

@Log4j2
@Epic("Автомобили. API")
@Feature("Покупка автомобиля")
public class BuyCarApiTest extends BaseTest {

    private CarResponse carResponse;
    private UserResponse userResponse;
    private UserRequest userRequest;
    private int carId, userId;

    @BeforeMethod
    public void createCarApi() {
        CarRequest carRequest = CarTestDataFactory.validCarTestDataAPI();
        carResponse = carAdapter.createApiCar(carRequest);
        carId = carResponse.getId();
    }

    @Test(testName = "Успешная покупка автомобиля")
    @Description("Проверка покупки автомобиля при наличии достаточной суммы у пользователя")
    void successBuyCar() {
        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();

        double startMoney = userResponse.getMoney();
        UserResponse userAfterBuyResponse = carAdapter.successBuyApiCar(userId, carId);
        List<CarResponse> carList = userAdapter.getCarsByUser(CarResponse.class, userId);

        assertTrue(carList.stream().anyMatch(car -> car.getId() == carId),
           "Автомобиль %s отсутствует в списке автомобилей пользователя".formatted(carId));

        BigDecimal bd = new BigDecimal(startMoney - carResponse.getPrice()).setScale(2, RoundingMode.HALF_UP);
        double roundedMoneyAfterBuy = bd.doubleValue();
        assertEquals(userAfterBuyResponse.getMoney(), roundedMoneyAfterBuy,
                "Сумма денег у пользователя уменьшилась не на стоимость автомобиля");
    }

    @Test(testName = "Ошибка при покупке автомобиля")
    @Description("Проверка покупки автомобиля при недостаточной сумме у пользователя")
    void buyNoEnoughMoneyCar() {
        userRequest = UserTestDataFactory.putUserTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();

        double startMoney = userResponse.getMoney();
        UserResponse userAfterBuyResponse = carAdapter.buyNotEnoughMoneyApiCar(userId, carId);
        userAdapter.getEmptyListCarsByUser(userId);

        assertEquals(userAfterBuyResponse.getMoney(), startMoney, "Сумма денег у пользователя изменилась!");

        carAdapter.deleteApiCar(carId);
        userAdapter.deleteUser(userId);
    }
}
