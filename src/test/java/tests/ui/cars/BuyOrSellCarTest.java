package tests.ui.cars;

import api.models.cars.CarRequest;
import api.models.cars.CarResponse;
import api.models.users.UserRequest;
import api.models.users.UserResponse;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
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
    Integer userId, carId;

    @BeforeMethod
    void createDataCar() {
        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, BUY_OR_SELL_CAR);
        CarRequest carRequest = CarTestDataFactory.validCarTestDataAPI();
        CarResponse carResponse = carAdapter.createCar(carRequest, 201, CarResponse.class);
        carId = carResponse.getId();
    }

    @BeforeMethod(onlyForGroups = {"haveMoneyUser"})
    void createDataValidUser() {
        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
    }

    @AfterMethod(onlyForGroups = {"deleteData"})
    void deleteTestData() {
        carAdapter.deleteApiCar(carId);
        userAdapter.deleteUser(userId);
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Успешная покупка автомобиля",
            groups = {"haveMoneyUser"})
    @Description("Проверка покупки автомобиля при наличии достаточной суммы у пользователя")
    void successBuyCar() {
        final String status = "Status: Successfully pushed, code: 200";

        new Input("id_send").fillField(String.valueOf(userId));
        new Input("car_send").fillField(String.valueOf(carId));
        baseSteps.selectRadioLabel(BUY);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Ошибка при покупке автомобиля",
            groups = {"deleteData"})
    @Description("Проверка покупки автомобиля при недостаточной сумме у пользователя")
    void buyNoEnoughMoneyCar() {
        userRequest = UserTestDataFactory.putUserTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
        final String status = "Status: AxiosError: Request failed with status code 406";

        new Input("id_send").fillField(String.valueOf(userId));
        new Input("car_send").fillField(String.valueOf(carId));
        baseSteps.selectRadioLabel(BUY);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Успешная продажа автомобиля",
            groups = {"haveMoneyUser", "deleteData"})
    @Description("Проверка продажи автомобиля из собственности пользователя")
    void successSellCar() {
        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
        final String status = "Status: Successfully pushed, code: 200";

        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
        carAdapter.buyCar(userId, carId, 200, UserResponse.class);

        new Input("id_send").fillField(String.valueOf(userId));
        new Input("car_send").fillField(String.valueOf(carId));
        baseSteps.selectRadioLabel(SELL);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }

    @Issue("Запрос на продажу выполняется успешно")
    @Owner("Кадырмятова А.В.")
    @Test(testName = "Ошибка при продаже автомобиля",
            groups = {"haveMoneyUser, deleteData"})
    @Description("Проверка продажи автомобиля не находящегося в собственности пользователя")
    void sellNoHaveCar() {
        userRequest = UserTestDataFactory.userMuchMoneyTestDataApi();
        userResponse = userAdapter.createUser(userRequest);
        userId = userResponse.getId();
        final String status = "Status: Successfully pushed, code: 406";

        new Input("id_send").fillField(String.valueOf(userId));
        new Input("car_send").fillField(String.valueOf(carId));
        baseSteps.selectRadioLabel(SELL);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status);
    }
}
