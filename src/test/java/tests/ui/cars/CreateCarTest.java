package tests.ui.cars;

import api.models.cars.CarResponse;
import io.qameta.allure.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ui.dto.cars.CarTestData;
import ui.dto.cars.CarTestDataFactory;
import ui.pages.cars.CarsPage;
import tests.ui.base.BaseTest;

import java.sql.ResultSet;

import static data.CarDao.getSelectCarByID;
import static data.CarDao.getSelectCarByModel;
import static io.qameta.allure.Allure.step;
import static java.lang.Integer.valueOf;
import static org.testng.Assert.assertTrue;
import static ui.enumUI.Dropdown.CARS;
import static ui.enumUI.TableType.CREATE_NEW_CARS;

@Epic("Автомобили")
@Feature("Создание автомобиля")
public class CreateCarTest extends BaseTest {

    @BeforeMethod
    void openPageCreateCar() {
        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, CREATE_NEW_CARS);
    }

    @Owner("Кадырмятова А.В.")
    @Test(testName = "Создание автомобиля с валидными данными")
    void successCreateCar() {
        CarTestData validCar = CarTestDataFactory.validCarTestDataUI();
        final String status = "Status: Successfully pushed, code: 201";

        carsPage.addNewCarUI(validCar);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New car ID:");
        String idCar = baseSteps.getNewObjectId();
        Integer carId = valueOf(idCar);

        step("Проверка получения созданного авто по ID", () ->
                carAdapter.getCar(carId, 200, CarResponse.class));

        step("Проверка записи по созданному авто в БД", () -> {
            connection.connect();
            ResultSet result = carDao.select(getSelectCarByID(idCar));
            while (result.next()) {
                carDao.verifyAttributesCar(validCar, result);
            }
        });

        step("Удаление тестовых данных", () ->
                carAdapter.deleteApiCar(carId));
    }

    @Owner("Кадырмятова А.В.")
    @Story("Создание автомобиля с невалидными данными")
    @Test(testName = "Создание автомобиля с пустым полем ",
            dataProvider = "Тестовые данные для негативных проверок создания автомобиля с пустыми полями",
            dataProviderClass = CarsPage.class)
    void unsuccessCreateCar(CarTestData carTestData) {
        final String status = "Status: Invalid request data";

        carsPage.addNewCarUI(carTestData);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();

        step("Проверка отсутствия записи по созданному авто в БД", () -> {
            connection.connect();
            assertTrue(carDao.emptySelect(getSelectCarByModel(carTestData)));
        });
    }

    @Owner("Кадырмятова А.В.")
    @Story("Создание автомобиля с невалидными данными")
    @Test(testName = "Создание автомобиля с числом в строковом поле",
            dataProvider = "Тестовые данные для проверок создания автомобиля с цифровым значением для строкового поля",
            dataProviderClass = CarsPage.class)
    void createCarWithNumbers(CarTestData carTestData) {
        final String status = "Status: AxiosError: Request failed with status code 400";

        carsPage.addNewCarUI(carTestData);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();

        step("Проверка отсутствия записи по созданному авто в БД", () -> {
            connection.connect();
            assertTrue(carDao.emptySelect(getSelectCarByModel(carTestData)));
        });
    }
}
