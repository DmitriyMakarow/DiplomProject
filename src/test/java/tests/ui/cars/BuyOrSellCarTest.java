package tests.ui.cars;

import api.models.cars.CarRequest;
import api.models.cars.CarResponse;
import api.models.users.UserRequest;
import api.models.users.UserResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import tests.ui.base.BaseTest;
import ui.dto.cars.CarTestDataFactory;
import ui.dto.users.UserTestDataFactory;
import ui.wrappers.Input;

import static ui.enumUI.Dropdown.CARS;
import static ui.enumUI.RadioLabel.BUY;
import static ui.enumUI.RadioLabel.SELL;
import static ui.enumUI.TableType.BUY_OR_SELL_CAR;

@Epic("Автомобили")
@Feature("Покупка/продажа автомобиля")
public class BuyOrSellCarTest extends BaseTest {

    private UserResponse userResponse;
    private UserRequest userRequest;
    String userId, carId;

    @BeforeMethod
    void openPageCreateCar() {
        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, BUY_OR_SELL_CAR);
        CarRequest carRequest = CarTestDataFactory.validCarTestDataAPI();
        CarResponse carResponse = carAdapter.createApiCar(carRequest);
        carId = String.valueOf(carResponse.getId());
    }

    @Test(testName = "Успешная покупка автомобиля")
    @Description("Проверка покупки автомобиля при наличии достаточной суммы у пользователя")
    void successBuyCar() {
        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = String.valueOf(userResponse.getId());
        final String status = "Status: Successfully pushed, code: 200";

        new Input("id_send").fillField(userId);
        new Input("car_send").fillField(carId);
        baseSteps.selectRadioLabel(BUY);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }

    @Test(testName = "Ошибка при покупке автомобиля")
    @Description("Проверка покупки автомобиля при недостаточной сумме у пользователя")
    void buyNoEnoughMoneyCar() {
        userRequest = UserTestDataFactory.putUserTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = String.valueOf(userResponse.getId());
        final String status = "Status: AxiosError: Request failed with status code 406";

        userRequest = UserTestDataFactory.putUserTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = String.valueOf(userResponse.getId());

        new Input("id_send").fillField(userId);
        new Input("car_send").fillField(carId);
        baseSteps.selectRadioLabel(BUY);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }

    @Test(testName = "Успешная продажа автомобиля")
    @Description("Проверка продажи автомобиля из собственности пользователя")
    void successSellCar() {
        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = String.valueOf(userResponse.getId());
        final String status = "Status: Successfully pushed, code: 200";

        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = String.valueOf(userResponse.getId());
        carAdapter.successBuyApiCar(Integer.valueOf(userId), Integer.valueOf(carId));

        new Input("id_send").fillField(userId);
        new Input("car_send").fillField(carId);
        baseSteps.selectRadioLabel(SELL);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }

    @Issue("Запрос на продажу выполняется успешно")
    @Test(testName = "Ошибка при продаже автомобиля")
    @Description("Проверка продажи автомобиля не находящегося в собственности пользователя")
    void sellNoHaveCar() {
        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = String.valueOf(userResponse.getId());
        final String status = "Status: Successfully pushed, code: 406";

        new Input("id_send").fillField(userId);
        new Input("car_send").fillField(carId);
        baseSteps.selectRadioLabel(SELL);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }
}
