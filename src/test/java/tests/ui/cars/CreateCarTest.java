package tests.ui.cars;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

import ui.dto.CarTestData;
import ui.dto.CarTestDataFactory;
import ui.pages.cars.CarsPage;
import tests.ui.base.BaseTest;
import ui.wrappers.Input;

import static ui.enumUI.Dropdown.CARS;
import static ui.enumUI.TableType.CREATE_NEW_CARS;
import static ui.pages.base.BasePage.faker;

@Epic("Автомобили")
@Feature("Создание автомобиля")
public class CreateCarTest extends BaseTest {

    @Test(testName = "Создание автомобиля с валидными данными")
    void successCreateCar() {
        CarTestData validCar = CarTestDataFactory.validCarTestDataUI();
        final String status = "Status: Successfully pushed, code: 201";

        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, CREATE_NEW_CARS);
        carsPage.addNewCarUI(validCar);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyGetIdObject("New car ID:");
    }

    @Story("Создание автомобиля с невалидными данными")
    @Test(testName = "Создание автомобиля с пустым полем ",
            dataProvider = "Тестовые данные для негативных проверок создания автомобиля",
            dataProviderClass = CarsPage.class)
    void unsuccessCreateCar(CarTestData carTestData) {
        final String status = "Status: Invalid request data";

        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, CREATE_NEW_CARS);
        carsPage.addNewCarUI(carTestData);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();
    }

    @Issue("")
    @Story("Создание автомобиля с невалидными данными")
    @Test(testName = "Создание автомобиля с несоответствующими данными")
    void createCarInvalidData() {
        CarTestData invalidCar = CarTestDataFactory.invalidCarTestDataUI();
        final String status = "Status: AxiosError: Request failed with status code 400";

        loginPage.authorization();
        baseSteps
                .showDropdown(CARS)
                .openTableFromDropdown(CARS, CREATE_NEW_CARS);
        carsPage.addNewCarUI(invalidCar);
        baseSteps
                .clickPushToApi()
                .verifyTextStatus(status)
                .verifyNoIdObject();
    }
}
